package me.huedev.broom.mixin.common.entity;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkeletonEntity.class)
public class SkeletonEntityMixin {
    @Inject(
            method = "method_637",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;method_1378()F",
                    shift = At.Shift.BEFORE
            )
    )
    public void broom_changeArrowYPosition(Entity arg, float f, CallbackInfo ci, @Local ArrowEntity var7) {
        var7.y -= 1.5;
    }

    @ModifyConstant(method = "method_637", constant = @Constant(doubleValue = 0.20000000298023224))
    private double broom_changeArrowYDirection(double constant) {
        return 0.2;
    }
}
