package net.huedev.broom.mixin.common.block;

import net.huedev.broom.util.ToolHelper;
import net.minecraft.block.GravelBlock;
import net.minecraft.block.SandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(GravelBlock.class)
public class GravelBlockMixin extends SandBlock {
    @Unique
    private boolean brokenBySilkTouchTool = false;

    public GravelBlockMixin(int id, int textureId) {
        super(id, textureId);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (ToolHelper.isUsingGoldenTool(player)) {
            brokenBySilkTouchTool = true;
        }

        player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        this.dropStacks(world, x, y, z, meta);
    }

    @Inject(at = @At("RETURN"), method = "getDroppedItemId", cancellable = true)
    public void broom_getDroppedItemId(int blockMeta, Random random, CallbackInfoReturnable<Integer> cir) {
        if (brokenBySilkTouchTool) {
            cir.setReturnValue(id);
            brokenBySilkTouchTool = false;
        }
    }
}
