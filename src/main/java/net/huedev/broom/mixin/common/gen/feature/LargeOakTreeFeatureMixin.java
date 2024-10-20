package net.huedev.broom.mixin.common.gen.feature;

import net.huedev.broom.block.BroomBlocks;
import net.minecraft.world.gen.feature.LargeOakTreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Random;

@Mixin(LargeOakTreeFeature.class)
public class LargeOakTreeFeatureMixin {
    @Shadow
    Random random;

    @ModifyConstant(method = "method_620", constant = @Constant(intValue = 17))
    private int broom_replaceLogs(int constant) {
        return BroomBlocks.OAK_LOG.id;
    }

    @ModifyConstant(method = "method_622", constant = @Constant(intValue = 17))
    private int broom_replaceLogsMore(int constant) {
        return BroomBlocks.OAK_LOG.id;
    }

    @ModifyArg(
            method = "method_615",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;method_200(IIII)Z"
            ),
            index = 3
    )
    private int broom_replaceLeaves(int blockId) {
        if (random.nextInt(400) == 0) {
            return BroomBlocks.APPLE_OAK_LEAVES.id;
        }
        return BroomBlocks.OAK_LEAVES.id;
    }
}
