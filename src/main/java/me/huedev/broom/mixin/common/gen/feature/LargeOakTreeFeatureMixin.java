package me.huedev.broom.mixin.common.gen.feature;

import me.huedev.broom.block.BroomBlocks;
import net.minecraft.world.gen.feature.LargeOakTreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LargeOakTreeFeature.class)
public class LargeOakTreeFeatureMixin {
    @ModifyConstant(method = "method_620", constant = @Constant(intValue = 17))
    private int broom_replaceLogs(int constant) {
        return BroomBlocks.OAK_LOG.id;
    }

    @ModifyConstant(method = "method_622", constant = @Constant(intValue = 17))
    private int broom_replaceLogsMore(int constant) {
        return BroomBlocks.OAK_LOG.id;
    }

    @ModifyConstant(method = "method_614", constant = @Constant(intValue = 18))
    private int broom_replaceLeaves(int constant) {
        return BroomBlocks.OAK_LEAVES.id;
    }
}
