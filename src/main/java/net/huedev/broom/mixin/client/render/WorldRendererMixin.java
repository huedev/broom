package net.huedev.broom.mixin.client.render;

import net.huedev.broom.util.BroomOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ItemParticle;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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

    @Shadow private Minecraft client;

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
            case 1009:
                this.world.playSound((double)j + 0.5, (double)k + 0.5, (double)l + 0.5, "random.click", 0.3F, 0.6F);
                ci.cancel();
                break;
            case 1010:
                this.world.playSound((double)j + 0.5, (double)k + 0.5, (double)l + 0.5, "random.click", 0.3F, 0.5F);
                ci.cancel();
                break;
            case 1011:
                this.world.playSound((double)j + 0.5, (double)k + 0.5, (double)l + 0.5, "random.fizz", 0.5F, 2.6F + (this.world.field_214.nextFloat() - this.world.field_214.nextFloat()) * 0.8F);
                ci.cancel();
                break;
        }
    }

    @Inject(
            method = "method_1153",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            )
    )
    public void broom_particles(String string, double d, double e, double f, double g, double h, double i, CallbackInfo ci) {
        if (string.equals("egg")) {
            this.client.field_2808.method_325(new ItemParticle(this.world, d, e, f, Item.EGG));
        }
    }
}
