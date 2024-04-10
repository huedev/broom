package me.huedev.broom.mixin.common;

import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author DanyGames2014
 */
@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
    @ModifyConstant(method = "method_956", constant = @Constant(doubleValue = 0.08))
    public double broom_reduceFishVerticalVelocity(double constant) {
        return 0.04;
    }
}
