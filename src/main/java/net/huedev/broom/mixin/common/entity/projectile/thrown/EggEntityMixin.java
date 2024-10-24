package net.huedev.broom.mixin.common.entity.projectile.thrown;

import net.minecraft.entity.projectile.thrown.EggEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EggEntity.class)
public class EggEntityMixin {
    @ModifyConstant(method = "tick", constant = @Constant(stringValue = "snowballpoof"))
    private String broom_useEggParticles(String constant) {
        return "egg";
    }
}
