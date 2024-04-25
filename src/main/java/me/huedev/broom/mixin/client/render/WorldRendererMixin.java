package me.huedev.broom.mixin.client.render;

import me.huedev.broom.util.BroomOptions;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DanyGames2014
 */
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow private World world;

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

    @Inject(method = "method_1152", at = @At("HEAD"), cancellable = true)
    public void broom_worldEvents(PlayerEntity arg, int i, int j, int k, int l, int m, CallbackInfo ci) {
        switch (i) {
            case 1006:
                this.world.playSound((double)j + 0.5, (double)k + 0.5, (double)l + 0.5, "random.door_open", 1.0F, this.world.field_214.nextFloat() * 0.1F + 0.9F);
                ci.cancel();
                break;
            case 1007:
                this.world.playSound((double)j + 0.5, (double)k + 0.5, (double)l + 0.5, "random.door_close", 1.0F, this.world.field_214.nextFloat() * 0.1F + 0.9F);
                ci.cancel();
                break;
            case 1008:
                this.world.playSound((double)j + 0.5, (double)k + 0.5, (double)l + 0.5, "broom:entity.sheep.shear", 1.0F, (this.world.field_214.nextFloat() - this.world.field_214.nextFloat()) * 0.2F + 1.0F);
                ci.cancel();
                break;
        }
    }
}
