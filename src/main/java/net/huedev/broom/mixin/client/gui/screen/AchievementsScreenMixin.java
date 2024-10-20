package net.huedev.broom.mixin.client.gui.screen;

import net.minecraft.client.gui.screen.AchievementsScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DanyGames2014
 */
@Mixin(AchievementsScreen.class)
public class AchievementsScreenMixin extends Screen {
    // TODO: Would be better to keep a reference to the original GameMenuScreen instance, but this works for now
    @Inject(method = "buttonClicked", at = @At(value = "HEAD"), cancellable = true)
    public void broom_setScreenToParent(ButtonWidget button, CallbackInfo ci){
        if (button.id == 1) {
            this.minecraft.setScreen(new GameMenuScreen());
            ci.cancel();
        }
    }
}
