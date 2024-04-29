package me.huedev.broom.mixin.common.block;

import me.huedev.broom.util.WorldHelper;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RepeaterBlock.class)
public class RepeaterBlockMixin {
    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    public void broom_canPlaceAt(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "canGrow", at = @At("HEAD"), cancellable = true)
    public void broom_canGrow(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z)) {
            cir.setReturnValue(true);
        }
    }
}
