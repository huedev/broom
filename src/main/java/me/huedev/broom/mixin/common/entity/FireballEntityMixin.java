package me.huedev.broom.mixin.common.entity;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FireballEntity.class)
public class FireballEntityMixin {
    @ModifyArg(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/Entity;I)Z"
            ),
            index = 1
    )
    private int broom_fireballDamagesGhast(int amount, @Local(ordinal = 0) HitResult var3) {
        if (var3.entity instanceof GhastEntity) {
            return 10;
        }
        return 0;
    }
}
