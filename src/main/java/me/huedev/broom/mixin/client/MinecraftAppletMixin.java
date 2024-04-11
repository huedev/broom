package me.huedev.broom.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DanyGames2014
 */
@Mixin(MinecraftApplet.class)
public class MinecraftAppletMixin {
    @Shadow
    private Minecraft field_2832;

    @Inject(method = "init", at = @At(value = "TAIL"), remap = false)
    public void broom_setIsAppletToFalse(CallbackInfo ci) {
        this.field_2832.isApplet = false;
    }
}
