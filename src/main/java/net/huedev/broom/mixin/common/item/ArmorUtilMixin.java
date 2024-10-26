package net.huedev.broom.mixin.common.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.item.ArmorUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ArmorUtil.class)
public class ArmorUtilMixin {
    @Overwrite
    public static double getVanillaArmorReduction(ItemStack armor) {
        double totalArmorProtection = 0;
        if (armor != null && armor.getItem() instanceof ArmorItem) {
            double armorProtection = ((ArmorItem) armor.getItem()).maxProtection;
            totalArmorProtection += armorProtection;
        }
        return (totalArmorProtection / 5);
    }
}
