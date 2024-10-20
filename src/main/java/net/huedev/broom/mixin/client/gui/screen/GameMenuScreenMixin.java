package net.huedev.broom.mixin.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameMenuScreen.class)
@Environment(EnvType.CLIENT)
public class GameMenuScreenMixin extends Screen {
    @ModifyConstant(method = "init", constant = @Constant(stringValue = "Save and quit to title"))
    private String broom_localizeQuitButtonText(String value) {
        return I18n.getTranslation("game_menu.save_and_quit");
    }

    @ModifyConstant(method = "init", constant = @Constant(stringValue = "Disconnect"))
    private String broom_localizeDisconnectButtonText(String value) {
        return I18n.getTranslation("game_menu.disconnect");
    }

    @ModifyConstant(method = "init", constant = @Constant(stringValue = "Back to game"))
    private String broom_localizeBackToGameButtonText(String value) {
        return I18n.getTranslation("game_menu.back_to_game");
    }

    @ModifyConstant(method = "init", constant = @Constant(stringValue = "Options..."))
    private String broom_localizeOptionsButtonText(String value) {
        return I18n.getTranslation("game_menu.options");
    }

    @ModifyConstant(method = "render", constant = @Constant(stringValue = "Saving level.."))
    private String broom_localizeSavingLevelText(String value) {
        return I18n.getTranslation("game_menu.saving_level");
    }

    @ModifyConstant(method = "render", constant = @Constant(stringValue = "Game menu"))
    private String broom_localizeGameMenuText(String value) {
        return I18n.getTranslation("game_menu.game_menu");
    }
}
