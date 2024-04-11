package me.huedev.broom.mixin.common.entity;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonEntityMixin extends Entity {
    public SkeletonEntityMixin(World world) {
        super(world);
    }

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

    @Redirect(method = "method_637", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;method_210(Lnet/minecraft/entity/Entity;)Z"))
    public boolean broom_createBurningArrow(World world, Entity arrowEntity) {
        if (this.fire > 0) {
            if (this.random.nextInt(0, 100) < 50) {
                arrowEntity.fire = 400;
            }
        }
        return world.method_210(arrowEntity);
    }
}
