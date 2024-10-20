package net.huedev.broom.mixin.common.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.huedev.broom.block.BroomBlocks;
import net.minecraft.block.SpongeBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpongeBlock.class)
public class SpongeBlockMixin {
    @WrapOperation(
            method = "onPlaced",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getMaterial(III)Lnet/minecraft/block/material/Material;"
            )
    )
    private Material broom_absorbWater(World world, int x, int y, int z, Operation<Material> original, @Local(argsOnly = true, ordinal = 0) int originalX, @Local(argsOnly = true, ordinal = 1) int originalY, @Local(argsOnly = true, ordinal = 2) int originalZ) {
        Material blockMaterial = original.call(world, x, y, z);

        if (blockMaterial == Material.WATER) {
            world.setBlockWithoutNotifyingNeighbors(x, y, z, 0);
            world.setBlockStateWithNotify(originalX, originalY, originalZ, BroomBlocks.WET_SPONGE.getDefaultState());
        }

        return blockMaterial;
    }
}
