package net.huedev.broom.mixin.common.block;

import net.huedev.broom.util.ToolHelper;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.TranslucentBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(GlassBlock.class)
public class GlassBlockMixin extends TranslucentBlock {
    @Unique
    private boolean brokenBySilkTouchTool = false;

    public GlassBlockMixin(int id, int textureId, Material material, boolean bl) {
        super(id, textureId, material, bl);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (ToolHelper.isUsingGoldenTool(player)) {
            brokenBySilkTouchTool = true;
        }

        player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        this.dropStacks(world, x, y, z, meta);
    }

    @Inject(at = @At("RETURN"), method = "getDroppedItemCount", cancellable = true)
    public void broom_getDroppedItemCount(Random random, CallbackInfoReturnable<Integer> cir) {
        if (brokenBySilkTouchTool) {
            cir.setReturnValue(1);
            brokenBySilkTouchTool = false;
        }
    }
}
