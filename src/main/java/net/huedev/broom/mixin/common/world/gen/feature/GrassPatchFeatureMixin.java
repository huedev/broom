package net.huedev.broom.mixin.common.world.gen.feature;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.huedev.broom.block.BroomBlocks;
import net.minecraft.world.gen.feature.GrassPatchFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GrassPatchFeature.class)
public class GrassPatchFeatureMixin {
    @Shadow
    private int tallGrassBlockMeta;

    @ModifyExpressionValue(
            method = "generate",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/gen/feature/GrassPatchFeature;tallGrassBlockId:I"
            )
    )
    private int broom_replaceGrass(int originalId) {
        return BroomBlocks.getGrassByMeta(tallGrassBlockMeta).id;
    }

    @ModifyExpressionValue(
            method = "generate",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/gen/feature/GrassPatchFeature;tallGrassBlockMeta:I"
            )
    )
    private int broom_replaceGrassMeta(int originalId) {
        return 0;
    }
}
