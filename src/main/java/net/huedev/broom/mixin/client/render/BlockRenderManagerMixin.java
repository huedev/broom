package net.huedev.broom.mixin.client.render;

import net.huedev.broom.block.BroomBlockProperties;
import net.huedev.broom.block.BroomBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRenderManager.class)
public abstract class BlockRenderManagerMixin {
    @Shadow
    public abstract boolean renderBlock(Block block, int x, int y, int z);

    @Shadow
    private BlockView blockView;

    /**
     * @author DanyGames2014
     */
    @Inject(method = "renderFence", at = @At(value = "HEAD"), cancellable = true)
    public void broom_renderConnectedFence(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        float var6 = 0.375F;
        float var7 = 0.625F;

        block.setBoundingBox(var6, 0.0F, var6, var7, 1.0F, var7);
        this.renderBlock(block, x, y, z);

        boolean connectedPosX = this.blockView.method_1780(x + 1, y, z) || this.blockView.getBlockId(x + 1, y, z) == Block.FENCE.id || broom_isValidFenceGate(blockView, x, y, z, x + 1, y, z);
        boolean connectedNegX = this.blockView.method_1780(x - 1, y, z) || this.blockView.getBlockId(x - 1, y, z) == Block.FENCE.id || broom_isValidFenceGate(blockView, x, y, z, x - 1, y, z);
        boolean connectedPosZ = this.blockView.method_1780(x, y, z + 1) || this.blockView.getBlockId(x, y, z + 1) == Block.FENCE.id || broom_isValidFenceGate(blockView, x, y, z, x, y, z + 1);
        boolean connectedNegZ = this.blockView.method_1780(x, y, z - 1) || this.blockView.getBlockId(x, y, z - 1) == Block.FENCE.id || broom_isValidFenceGate(blockView, x, y, z, x, y, z - 1);

        boolean connectedX = connectedPosX || connectedNegX;
        boolean connectedZ = connectedPosZ || connectedNegZ;

        if (!connectedX && !connectedZ) {
            connectedX = true;
        }

        var6 = 0.4375F;
        var7 = 0.5625F;
        float minY = 0.75F;
        float maxY = 0.9375F;
        float minX = connectedNegX ? 0.0F : var6;
        float maxX = connectedPosX ? 1.0F : var7;
        float minZ = connectedNegZ ? 0.0F : var6;
        float maxZ = connectedPosZ ? 1.0F : var7;

        if (connectedX) {
            block.setBoundingBox(minX, minY, var6, maxX, maxY, var7);
            this.renderBlock(block, x, y, z);
        }

        if (connectedZ) {
            block.setBoundingBox(var6, minY, minZ, var7, maxY, maxZ);
            this.renderBlock(block, x, y, z);
        }

        minY = 0.375F;
        maxY = 0.5625F;

        if (connectedX) {
            block.setBoundingBox(minX, minY, var6, maxX, maxY, var7);
            this.renderBlock(block, x, y, z);
        }

        if (connectedZ) {
            block.setBoundingBox(var6, minY, minZ, var7, maxY, maxZ);
            this.renderBlock(block, x, y, z);
        }

        block.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        cir.setReturnValue(true);
    }

    @Unique
    private boolean broom_isValidFenceGate(BlockView view, int originalX, int originalY, int originalZ, int targetX, int targetY, int targetZ) {
        if (!(view instanceof BlockStateView bsView)) {
            return false;
        }

        if (!bsView.getBlockState(targetX, targetY, targetZ).isOf(BroomBlocks.FENCE_GATE)) {
            return false;
        }

        BlockState state = bsView.getBlockState(targetX, targetY, targetZ);
        Direction facing = state.get(BroomBlockProperties.FACING);

        switch (facing.getAxis()) {
            case X -> {
                if (originalZ != targetZ) {
                    return true;
                }
            }
            case Z -> {
                if (originalX != targetX) {
                    return true;
                }
            }
        }

        return false;
    }

    /*
    @Inject(method = "renderStairs", at = @At("HEAD"), cancellable = true)
    private void broom_renderStairs(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
        if (this.blockView instanceof BlockStateView stateView && block instanceof StairsBlock) {
            BlockState state = stateView.getBlockState(x, y, z);
            Direction facing = state.get(BroomBlockProperties.FACING);
            TopBottom type = state.get(BroomBlockProperties.TOP_BOTTOM);

            switch (type) {
                case BOTTOM -> {
                    switch (facing) {
                        case WEST -> {
                            block.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
                            this.renderBlock(block, x, y, z);
                            block.setBoundingBox(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
                            this.renderBlock(block, x, y, z);
                        }
                        case NORTH -> {
                            block.setBoundingBox(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
                            this.renderBlock(block, x, y, z);
                            block.setBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
                            this.renderBlock(block, x, y, z);
                        }
                        case EAST -> {
                            block.setBoundingBox(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
                            this.renderBlock(block, x, y, z);
                            block.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                            this.renderBlock(block, x, y, z);
                        }
                        case SOUTH -> {
                            block.setBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
                            this.renderBlock(block, x, y, z);
                            block.setBoundingBox(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                            this.renderBlock(block, x, y, z);
                        }
                    }
                }
                case TOP -> {
                    switch (facing) {
                        case WEST -> {
                            block.setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                            this.renderBlock(block, x, y, z);
                            block.setBoundingBox(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
                            this.renderBlock(block, x, y, z);
                        }
                        case NORTH -> {
                            block.setBoundingBox(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                            this.renderBlock(block, x, y, z);
                            block.setBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
                            this.renderBlock(block, x, y, z);
                        }
                        case EAST -> {
                            block.setBoundingBox(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                            this.renderBlock(block, x, y, z);
                            block.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                            this.renderBlock(block, x, y, z);
                        }
                        case SOUTH -> {
                            block.setBoundingBox(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
                            this.renderBlock(block, x, y, z);
                            block.setBoundingBox(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                            this.renderBlock(block, x, y, z);
                        }
                    }
                }
            }

            info.setReturnValue(true);
        }
    }
    */
}
