package me.huedev.broom.mixin.common.block;

import me.huedev.broom.util.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.IceBlock;
import net.minecraft.block.Material;
import net.minecraft.class_221;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(IceBlock.class)
public class IceBlockMixin extends class_221 {
    @Unique
    private boolean brokenBySilkTouchTool = false;

    public IceBlockMixin(int id, int textureId, Material material, boolean bl) {
        super(id, textureId, material, bl);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (ToolHelper.isUsingGoldenTool(player)) {
            brokenBySilkTouchTool = true;
        }

        if (!brokenBySilkTouchTool) {
            Material var7 = world.method_1779(x, y - 1, z);
            if (var7.method_907() || var7.method_893()) {
                world.setBlock(x, y, z, Block.FLOWING_WATER.id);
            }
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
