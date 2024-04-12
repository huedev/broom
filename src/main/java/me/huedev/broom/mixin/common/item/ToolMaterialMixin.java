package me.huedev.broom.mixin.common.item;

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
}
