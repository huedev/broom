package me.huedev.broom.mixin.client.render;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DanyGames2014
 */
@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Inject(method = "method_821", at = @At("HEAD"), cancellable = true)
    public void broom_disableDebugEntityIds(LivingEntity entity, double x, double y, double z, CallbackInfo ci) {
        ci.cancel();
    }
}
