package me.huedev.broom.mixin.common.gen.feature;

import me.huedev.broom.block.BroomBlocks;
import net.minecraft.world.gen.feature.PumpkinPatchFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PumpkinPatchFeature.class)
public class PumpkinPatchFeatureMixin {
    @ModifyArg(
            method = "generate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;method_154(IIIII)Z"
            ),
            index = 3
    )
    private int broom_replacePumpkins(int originalId) {
        return BroomBlocks.PUMPKIN.id;
    }
}
