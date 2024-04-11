package me.huedev.broom.mixin.common.entity;

import net.minecraft.entity.passive.ChickenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author DanyGames2014
 */
@Mixin(ChickenEntity.class)
public class ChickenEntityMixin {
    @ModifyConstant(method = "<init>", constant = @Constant(floatValue = 0.3F))
    public float broom_modifyWidth(float constant) {
        return 0.4F;
    }

    @ModifyConstant(method = "<init>", constant = @Constant(floatValue = 0.4F))
    public float broom_modifyHeight(float constant) {
        return 0.7F;
    }
}
