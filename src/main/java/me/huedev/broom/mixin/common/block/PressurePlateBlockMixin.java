package me.huedev.broom.mixin.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author DanyGames2014
 */
@Mixin(PressurePlateBlock.class)
public class PressurePlateBlockMixin {
    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    public void broom_allowPlacementOnFences(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (world.getBlockId(x, y - 1, z) == Block.FENCE.id) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "neighborUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/PressurePlateBlock;dropStacks(Lnet/minecraft/world/World;IIII)V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void broom_preventBreakingOnUpdate(World world, int x, int y, int z, int id, CallbackInfo ci) {
        if (world.getBlockId(x, y - 1, z) == Block.FENCE.id) {
            ci.cancel();
        }
    }
}
