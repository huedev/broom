package net.huedev.broom.mixin.common.world.gen.feature;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.huedev.broom.block.BroomBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.BirchTreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author paulevsGitch
 */
@Mixin(BirchTreeFeature.class)
public class BirchTreeFeatureMixin {
    @ModifyExpressionValue(
            method = "generate",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/Block;LOG:Lnet/minecraft/block/Block;"
            )
    )
    private Block broom_replaceLogs(Block originalBlock) {
        return BroomBlocks.BIRCH_LOG;
    }

    @ModifyExpressionValue(
            method = "generate",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/LeavesBlock;id:I"
            )
    )
    private int broom_replaceLeaves(int originalId) {
        return BroomBlocks.BIRCH_LEAVES.id;
    }
}
