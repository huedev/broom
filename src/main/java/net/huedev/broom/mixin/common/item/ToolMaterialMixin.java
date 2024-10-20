package net.huedev.broom.mixin.common.item;

import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ToolMaterial.class)
public class ToolMaterialMixin {
    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 0))
    private static int broom_increaseGoldMiningLevel(int constant) {
        return 2;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 32))
    private static int broom_increaseGoldDurability(int constant) {
        return 59;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 12.0F))
    private static float broom_decreaseGoldMiningSpeed(float constant) {
        return 6.0F;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 8.0F))
    private static float broom_increaseDiamondMiningSpeed(float constant) {
        return 14.0F;
    }
}
