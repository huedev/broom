package net.huedev.broom.mixin.common.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.huedev.broom.util.WorldHelper;
import net.minecraft.block.RailBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RailBlock.class)
public abstract class RailBlockMixin {
    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    public void broom_canPlaceAt(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z)) {
            cir.setReturnValue(true);
        }
    }

    @ModifyExpressionValue(
            method = "neighborUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;shouldSuffocate(III)Z",
                    ordinal = 0
            )
    )
    private boolean broom_preventBreakingOnUpdateNegY(boolean original, @Local(argsOnly = true) World world, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        return original || WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z);
    }

    @ModifyExpressionValue(
            method = "neighborUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;shouldSuffocate(III)Z",
                    ordinal = 1
            )
    )
    private boolean broom_preventBreakingOnUpdatePosX(boolean original, @Local(argsOnly = true) World world, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        return original || WorldHelper.isBlockStateFloorSupport(world, x + 1, y, z);
    }

    @ModifyExpressionValue(
            method = "neighborUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;shouldSuffocate(III)Z",
                    ordinal = 2
            )
    )
    private boolean broom_preventBreakingOnUpdateNegX(boolean original, @Local(argsOnly = true) World world, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        return original || WorldHelper.isBlockStateFloorSupport(world, x - 1, y, z);
    }

    @ModifyExpressionValue(
            method = "neighborUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;shouldSuffocate(III)Z",
                    ordinal = 3
            )
    )
    private boolean broom_preventBreakingOnUpdateNegZ(boolean original, @Local(argsOnly = true) World world, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        return original || WorldHelper.isBlockStateFloorSupport(world, x, y, z - 1);
    }

    @ModifyExpressionValue(
            method = "neighborUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;shouldSuffocate(III)Z",
                    ordinal = 4
            )
    )
    private boolean broom_preventBreakingOnUpdatePosZ(boolean original, @Local(argsOnly = true) World world, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        return original || WorldHelper.isBlockStateFloorSupport(world, x, y, z + 1);
    }

    /*
    @Redirect(method = "neighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;method_1780(III)Z", ordinal = 0))
    private boolean broom_preventBreakingOnUpdate(World world, int x, int y, int z) {
        return !world.method_1780(x, y - 1, z) && !WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z);
    }
    */
}
