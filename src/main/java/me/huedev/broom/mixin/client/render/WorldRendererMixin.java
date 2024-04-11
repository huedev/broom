package me.huedev.broom.mixin.client.render;

import me.huedev.broom.util.BroomOptions;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DanyGames2014
 */
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Redirect(method = "method_1552", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/Dimension;method_1764()F"))
    public float broom_changeCloudHeight(Dimension dimension) {
        return BroomOptions.getCloudHeight();
    }

    @Redirect(method = "method_1556", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/Dimension;method_1764()F"))
    public float broom_changeFancyCloudHeight(Dimension dimension) {
        return BroomOptions.getCloudHeight();
    }

    @Inject(method = "method_1552", at = @At(value = "HEAD"), cancellable = true)
    public void broom_toggleClouds(float par1, CallbackInfo ci) {
        if (!BroomOptions.clouds) {
            ci.cancel();
        }
    }
}
