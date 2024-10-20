package net.huedev.broom.mixin.common.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlimeEntity.class)
public class SlimeEntityMixin extends LivingEntity {
    public SlimeEntityMixin(World arg) {
        super(arg);
    }

    @Inject(method = "markDead", at = @At("HEAD"))
    public void broom_splitOnDeathFix(CallbackInfo ci) {
        if (this.health < 0) {
            this.health = 0;
        }
    }
}
