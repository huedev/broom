package me.huedev.broom.mixin.common.block;

import me.huedev.broom.util.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(OreBlock.class)
public class OreBlockMixin extends Block {
    @Unique
    private boolean brokenBySilkTouchTool = false;

    public OreBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
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
        }
    }

    @Inject(at = @At("RETURN"), method = "getDroppedItemId", cancellable = true)
    public void broom_getDroppedItemId(int blockMeta, Random random, CallbackInfoReturnable<Integer> cir) {
        if (brokenBySilkTouchTool) {
            cir.setReturnValue(id);
            brokenBySilkTouchTool = false;
        }
    }
}
