package net.huedev.broom.mixin.common.block;

import net.huedev.broom.block.BroomBlockProperties;
import net.huedev.broom.block.BroomBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.Material;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FenceBlock.class)
public class FenceBlockMixin extends Block {
    @Unique
    private static World world;

    public FenceBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    private void broom_bypassPlacementRestriction(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canPlaceAt(world, x, y, z));
    }

    @Unique
    private Box broom_generateBox(World world, int x, int y, int z) {
        boolean connectPosX;
        boolean connectNegX;
        boolean connectPosZ;
        boolean connectNegZ;
        int fenceId = Block.FENCE.id;

        connectPosX = world.method_1780(x + 1, y, z) || world.getBlockId(x + 1, y, z) == fenceId || broom_isValidFenceGate(world, x, y, z, x + 1, y, z);
        connectNegX = world.method_1780(x - 1, y, z) || world.getBlockId(x - 1, y, z) == fenceId || broom_isValidFenceGate(world, x, y, z, x - 1, y, z);
        connectPosZ = world.method_1780(x, y, z + 1) || world.getBlockId(x, y, z + 1) == fenceId || broom_isValidFenceGate(world, x, y, z, x, y, z + 1);
        connectNegZ = world.method_1780(x, y, z - 1) || world.getBlockId(x, y, z - 1) == fenceId || broom_isValidFenceGate(world, x, y, z, x, y, z - 1);

        return Box.create(
                connectNegX ? 0.0F : 0.375F,
                0.0F,
                connectNegZ ? 0.0F : 0.375F,
                connectPosX ? 1.0F : 0.625F,
                1.0F,
                connectPosZ ? 1.0F : 0.625F
        );
    }

    @Unique
    private boolean broom_isValidFenceGate(World world, int originalX, int originalY, int originalZ, int targetX, int targetY, int targetZ) {
        if (world.getBlockId(targetX, targetY, targetZ) != BroomBlocks.FENCE_GATE.id) {
            return false;
        }

        BlockState state = world.getBlockState(targetX, targetY, targetZ);
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

    @Unique
    private Box broom_generateBox(World world, int x, int y, int z, boolean collider) {
        Box box = broom_generateBox(world, x, y, z);

        box.minX += x;
        box.minY += y;
        box.minZ += z;
        box.maxX += x;
        box.maxY += y;
        box.maxZ += z;

        if (collider) {
            box.maxY += 0.5F;
        }

        return box;
    }

    @Inject(method = "getCollisionShape", at = @At("RETURN"), cancellable = true)
    public void broom_getCollisionShape(World world, int x, int y, int z, CallbackInfoReturnable<Box> cir) {
        cir.setReturnValue(broom_generateBox(world, x, y, z, true));
    }

    @Override
    public Box getBoundingBox(World world, int x, int y, int z) {
        FenceBlockMixin.world = world;
        return broom_generateBox(world, x, y, z, false);
    }

    @Override
    public void updateBoundingBox(BlockView blockView, int x, int y, int z) {
        if (world == null) {
            return;
        }

        Box box = broom_generateBox(world, x, y, z);
        setBoundingBox(
                (float) box.minX,
                (float) box.minY,
                (float) box.minZ,
                (float) box.maxX,
                (float) box.maxY,
                (float) box.maxZ
        );
    }
}
