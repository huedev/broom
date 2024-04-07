package me.huedev.broom.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.huedev.broom.block.BroomBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.PineTreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author paulevs
 */
@Mixin(PineTreeFeature.class)
public class PineTreeFeatureMixin {
    @ModifyExpressionValue(
            method = "generate",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/Block;LOG:Lnet/minecraft/block/Block;"
            )
    )
    private Block broom_replaceLogs(Block originalBlock) {
        return BroomBlocks.SPRUCE_LOG;
    }

    @ModifyExpressionValue(
            method = "generate",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/LeavesBlock;id:I"
            )
    )
    private int broom_replaceLeaves(int originalId) {
        return BroomBlocks.SPRUCE_LEAVES.id;
    }
}
