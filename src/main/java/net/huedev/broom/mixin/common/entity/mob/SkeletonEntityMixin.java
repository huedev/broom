package net.huedev.broom.mixin.common.entity.mob;

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
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getEyeHeight()F",
                    shift = At.Shift.BEFORE
            )
    )
    public void broom_changeArrowYPosition(Entity arg, float f, CallbackInfo ci, @Local ArrowEntity var7) {
        var7.y -= 1.5;
    }

    @ModifyConstant(method = "attack", constant = @Constant(doubleValue = 0.20000000298023224))
    private double broom_changeArrowYDirection(double constant) {
        return 0.2;
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    public boolean broom_createBurningArrow(World world, Entity arrowEntity) {
        if (this.fireTicks > 0) {
            if (this.random.nextInt(0, 100) < 50) {
                arrowEntity.fireTicks = 400;
            }
        }
        return world.spawnEntity(arrowEntity);
    }
}
