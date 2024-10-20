package net.huedev.broom.mixin.common.gen.feature;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.huedev.broom.block.BroomBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.OakTreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Random;

@Mixin(OakTreeFeature.class)
public class OakTreeFeatureMixin {
    @ModifyExpressionValue(
            method = "generate",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/Block;LOG:Lnet/minecraft/block/Block;"
            )
    )
    private Block broom_replaceLogs(Block originalBlock) {
        return BroomBlocks.OAK_LOG;
    }

    @ModifyExpressionValue(
            method = "generate",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/LeavesBlock;id:I"
            )
    )
    private int broom_replaceLeaves(int originalId, @Local(argsOnly = true) Random random) {
        if (random.nextInt(400) == 0) {
            return BroomBlocks.APPLE_OAK_LEAVES.id;
        }
        return BroomBlocks.OAK_LEAVES.id;
    }
}
