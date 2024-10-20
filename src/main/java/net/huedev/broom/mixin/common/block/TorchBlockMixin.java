package net.huedev.broom.mixin.common.block;

import net.huedev.broom.util.WorldHelper;
import net.minecraft.block.TorchBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TorchBlock.class)
public class TorchBlockMixin {
    @Inject(method = "canPlaceOn", at = @At("HEAD"), cancellable = true)
    public void broom_placeOnTopSlabsAndStairs(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (WorldHelper.isBlockStateFloorSupport(world, x, y, z)) {
            cir.setReturnValue(true);
        }
    }
}
