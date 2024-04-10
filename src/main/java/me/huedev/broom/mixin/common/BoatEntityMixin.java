package me.huedev.broom.mixin.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author DanyGames2014
 */
@Mixin(BoatEntity.class)
public class BoatEntityMixin {
    @Inject(
            method = "method_1323",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;method_1376(Lnet/minecraft/entity/Entity;)V",
                    shift = At.Shift.AFTER
            )
    )
    public void broom_fixDismountPosition(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (player.field_1594 == null) {
            player.method_1340(player.x, player.y + 0.01, player.z);
        }
    }
}
