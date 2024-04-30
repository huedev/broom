package me.huedev.broom.mixin.common.block;

import me.huedev.broom.block.BroomBlockProperties;
import me.huedev.broom.block.BroomSlabBlock;
import me.huedev.broom.util.ToolHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.Material;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(GrassBlock.class)
public class GrassBlockMixin extends Block {
    @Unique
    private boolean brokenBySilkTouchTool = false;

    public GrassBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getTextureId(BlockView blockView, int x, int y, int z, int side) {
        if (side == 1) {
            return 0;
        } else if (side == 0) {
            return 2;
        } else {
            Material aboveMaterial = blockView.method_1779(x, y + 1, z);
            BlockState aboveState = ((BlockStateView)blockView).getBlockState(x, y + 1, z);
            if (aboveMaterial == Material.field_998 || aboveMaterial == Material.field_999) {
                if (aboveState.getBlock() instanceof StairsBlock || aboveState.getBlock() instanceof BroomSlabBlock) {
                    BroomBlockProperties.TopBottom topBottom = aboveState.get(BroomBlockProperties.TOP_BOTTOM);
                    return topBottom == BroomBlockProperties.TopBottom.TOP ? 3 : 68;
                }
                return 68;
            }
            return 3;
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (ToolHelper.isUsingSilkTouchTool(player)) {
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

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        if (brokenBySilkTouchTool) {
            brokenBySilkTouchTool = false;
            return id;
        }
        return Block.DIRT.id;
    }
}
