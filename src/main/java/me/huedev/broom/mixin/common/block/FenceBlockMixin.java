package me.huedev.broom.mixin.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.Material;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
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

        connectPosX = world.method_1780(x + 1, y, z) || world.getBlockId(x + 1, y, z) == fenceId;
        connectNegX = world.method_1780(x - 1, y, z) || world.getBlockId(x - 1, y, z) == fenceId;
        connectPosZ = world.method_1780(x, y, z + 1) || world.getBlockId(x, y, z + 1) == fenceId;
        connectNegZ = world.method_1780(x, y, z - 1) || world.getBlockId(x, y, z - 1) == fenceId;

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
