package net.huedev.broom.mixin.common.world;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {
    @Shadow public abstract boolean canTransferPowerInDirection(int x, int y, int z, int direction);

    @Inject(method = "canTransferPower", at = @At("HEAD"), cancellable = true)
    public void broom_addSelfForPowerTransferCheck(int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (this.canTransferPowerInDirection(x, y, z, 0)) {
            cir.setReturnValue(true);
        }
    }
}
