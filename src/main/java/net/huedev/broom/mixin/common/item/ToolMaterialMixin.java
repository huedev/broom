package net.huedev.broom.mixin.common.item;

import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ToolMaterial.class)
public class ToolMaterialMixin {
    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 59))
    private static int broom_tweakWoodDurability(int constant) {
        return 64;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 131))
    private static int broom_tweakStoneDurability(int constant) {
        return 128;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 250))
    private static int broom_tweakIronDurability(int constant) {
        return 384;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 1561))
    private static int broom_tweakDiamondDurability(int constant) {
        return 1536;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 8.0F))
    private static float broom_tweakDiamondMiningSpeed(float constant) {
        return 14.0F;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 0))
    private static int broom_tweakGoldMiningLevel(int constant) {
        return 2;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 32))
    private static int broom_tweakGoldDurability(int constant) {
        return 256;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 12.0F))
    private static float broom_tweakGoldMiningSpeed(float constant) {
        return 6.0F;
    }
}
