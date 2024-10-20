package net.huedev.broom.mixin.client.option;

import net.huedev.broom.util.BroomOptions;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * @author DanyGames2014
 */
@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Shadow
    protected abstract float parseFloat(String string);

    @Inject(method = "setFloat", at = @At(value = "HEAD"))
    public void broom_setFloat(Option option, float value, CallbackInfo ci) {
        if (option == BroomOptions.fovOption) {
            BroomOptions.fov = value;
        }

        if (option == BroomOptions.cloudHeightOption) {
            BroomOptions.cloudHeight = value;
        }

        if (option == BroomOptions.fpsLimitOption) {
            BroomOptions.fpsLimit = value;
        }
    }

    @Inject(method = "setInt", at = @At(value = "HEAD"))
    public void broom_setBoolean(Option option, int value, CallbackInfo ci) {
        if (option == BroomOptions.cloudsOption) {
            BroomOptions.clouds = !BroomOptions.clouds;
        }
    }

    @Inject(method = "getFloat", at = @At(value = "HEAD"), cancellable = true)
    public void broom_getFloat(Option option, CallbackInfoReturnable<Float> cir) {
        if (option == BroomOptions.fovOption) {
            cir.setReturnValue(BroomOptions.fov);
        }

        if (option == BroomOptions.cloudHeightOption) {
            cir.setReturnValue(BroomOptions.cloudHeight);
        }

        if (option == BroomOptions.fpsLimitOption) {
            cir.setReturnValue(BroomOptions.fpsLimit);
        }
    }

    @Inject(method = "getBoolean", at = @At(value = "HEAD"), cancellable = true)
    public void broom_getBoolean(Option option, CallbackInfoReturnable<Boolean> cir) {
        if (option == BroomOptions.cloudsOption) {
            cir.setReturnValue(BroomOptions.clouds);
        }
    }

    @Inject(method = "getString", at = @At(value = "HEAD"), cancellable = true)
    public void broom_getTranslatedValue(Option option, CallbackInfoReturnable<String> cir) {
        TranslationStorage translations = TranslationStorage.getInstance();

        if (option == BroomOptions.fovOption) {
            float value = BroomOptions.fov;
            if (value == 0.0f) {
                cir.setReturnValue(translations.get("options.broom.fov") + ": " + translations.get("options.broom.fov.default"));
            } else if (value == 1.0f) {
                cir.setReturnValue(translations.get("options.broom.fov") + ": " + translations.get("options.broom.fov.max"));
            } else {
                cir.setReturnValue(translations.get("options.broom.fov") + ": " + BroomOptions.getFovInDegrees());
            }
        }

        if (option == BroomOptions.cloudsOption) {
            String optionName = translations.get("options.broom.clouds") + ": " + (BroomOptions.clouds ? translations.get("options.on") : translations.get("options.off"));
            cir.setReturnValue(optionName);
        }

        if (option == BroomOptions.cloudHeightOption) {
            String optionName = translations.get("options.broom.cloud_height") + ": " + BroomOptions.getCloudHeight();
            cir.setReturnValue(optionName);
        }

        if (option == BroomOptions.fpsLimitOption) {
            int value = BroomOptions.getFpsLimitValue();
            if (value >= 300) {
                cir.setReturnValue(translations.get("options.broom.fps_limit") + ": " + translations.get("options.broom.fps_limit.max"));
            } else {
                cir.setReturnValue(translations.get("options.broom.fps_limit") + ": " + value);
            }
        }
    }

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Ljava/lang/String;split(Ljava/lang/String;)[Ljava/lang/String;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void broom_load(CallbackInfo ci, BufferedReader bufferedReader, String string) {
        String[] stringArray = string.split(":");

        if (stringArray[0].equals("fov")) {
            BroomOptions.fov = this.parseFloat(stringArray[1]);
        }

        if (stringArray[0].equals("clouds")) {
            BroomOptions.clouds = stringArray[1].equals("true");
        }

        if (stringArray[0].equals("cloud_height")) {
            BroomOptions.cloudHeight = this.parseFloat(stringArray[1]);
        }

        if (stringArray[0].equals("fps_limit")) {
            BroomOptions.fpsLimit = this.parseFloat(stringArray[1]);
        }
    }

    @Inject(method = "save", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;close()V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void broom_saveOptions(CallbackInfo ci, PrintWriter printWriter) {
        printWriter.println("fov:" + BroomOptions.fov);
        printWriter.println("clouds:" + BroomOptions.clouds);
        printWriter.println("cloud_height:" + BroomOptions.cloudHeight);
        printWriter.println("fps_limit:" + BroomOptions.fpsLimit);
    }
}
