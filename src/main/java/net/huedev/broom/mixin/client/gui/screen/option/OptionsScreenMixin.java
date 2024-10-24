package net.huedev.broom.mixin.client.gui.screen.option;

import net.huedev.broom.util.BroomOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.option.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;

/**
 * @author DanyGames2014
 */
@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {
    @Shadow
    private static Option[] RENDER_OPTIONS;

    static {
        RENDER_OPTIONS = Arrays.copyOf(RENDER_OPTIONS, RENDER_OPTIONS.length + 1);
        OptionsScreenMixin.RENDER_OPTIONS[OptionsScreenMixin.RENDER_OPTIONS.length - 1] = BroomOptions.fovOption;
    }
}
