package net.huedev.broom.mixin.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author DanyGames2014
 */
@Mixin(MinecartEntity.class)
public class MinecartEntityMixin {
    @Inject(method = "getCollisionAgainstShape", at = @At("HEAD"), cancellable = true)
    public void broom_preventCollisionWithItems(Entity other, CallbackInfoReturnable<Box> cir) {
        if (other instanceof ItemEntity || other instanceof ArrowEntity) {
            cir.setReturnValue(null);
        }
    }
}
