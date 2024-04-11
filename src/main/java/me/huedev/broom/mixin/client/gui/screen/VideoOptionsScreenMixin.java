package me.huedev.broom.mixin.client.gui.screen;

import me.huedev.broom.util.BroomOptions;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

/**
 * @author DanyGames2014
 */
@Mixin(VideoOptionsScreen.class)
public class VideoOptionsScreenMixin {
    @Shadow
    private static Option[] field_2003;

    @Inject(
            method = "buttonClicked",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/option/VideoOptionsScreen;init(Lnet/minecraft/client/Minecraft;II)V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void broom_sliderFix(ButtonWidget button, CallbackInfo ci) {
        if (button.id != 12) {
            ci.cancel();
        }
    }

    static {
        field_2003 = Arrays.copyOf(field_2003, field_2003.length + 2);
        VideoOptionsScreenMixin.field_2003[VideoOptionsScreenMixin.field_2003.length - 2] = BroomOptions.cloudsOption;
        VideoOptionsScreenMixin.field_2003[VideoOptionsScreenMixin.field_2003.length - 1] = BroomOptions.cloudHeightOption;
        VideoOptionsScreenMixin.field_2003[3] = BroomOptions.fpsLimitOption;
    }
}
