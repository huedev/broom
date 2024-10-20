package net.huedev.broom.mixin.client.option;

import net.huedev.broom.util.BroomOptions;
import net.minecraft.client.option.Option;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author DanyGames2014
 */
@Mixin(Option.class)
public class OptionMixin {
    @Shadow
    @Final
    @Mutable
    private static Option[] field_1113;

    @Invoker(value = "<init>")
    private static Option broom_newOption(String internalName, int internalId, String translationKey, boolean slider, boolean toggle) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = 179, target = "Lnet/minecraft/client/option/Option;field_1113:[Lnet/minecraft/client/option/Option;", shift = At.Shift.AFTER))
    private static void broom_addOptions(CallbackInfo ci) {
        ArrayList<Option> options = new ArrayList<>(Arrays.asList(field_1113));
        Option last = options.get(options.size() - 1);

        Option FOV;
        BroomOptions.fovOption = FOV = OptionMixin.broom_newOption("FOV", last.ordinal(), "options.fov", true, false);
        options.add(FOV);

        Option CLOUDS;
        BroomOptions.cloudsOption = CLOUDS = OptionMixin.broom_newOption("CLOUDS", last.ordinal() + 1, "options.clouds", false, true);
        options.add(CLOUDS);

        Option CLOUD_HEIGHT;
        BroomOptions.cloudHeightOption = CLOUD_HEIGHT = OptionMixin.broom_newOption("CLOUD_HEIGHT", last.ordinal() + 2, "options.cloud_height", true, false);
        options.add(CLOUD_HEIGHT);

        Option FPS_LIMIT;
        BroomOptions.fpsLimitOption = FPS_LIMIT = OptionMixin.broom_newOption("FPS_LIMIT", last.ordinal() + 3, "options.fps_limit", true, false);
        options.add(FPS_LIMIT);

        field_1113 = options.toArray(new Option[0]);
    }
}
