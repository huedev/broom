package net.huedev.broom.mixin.client.gui.screen;

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
    private static Option[] field_2764;

    static {
        field_2764 = Arrays.copyOf(field_2764, field_2764.length + 1);
        OptionsScreenMixin.field_2764[OptionsScreenMixin.field_2764.length - 1] = BroomOptions.fovOption;
    }
}
