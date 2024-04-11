package me.huedev.broom.mixin.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author Jadestrouble
 */
@Mixin(MusicDiscItem.class)
public class MusicDiscItemMixin implements CustomTooltipProvider {
    @Shadow
    @Final
    public String sound;

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        return new String[] {"§b" + originalTooltip, "§7" + "C418 - " + sound};
    }
}
