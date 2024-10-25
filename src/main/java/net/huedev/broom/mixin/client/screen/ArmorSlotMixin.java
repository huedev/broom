package net.huedev.broom.mixin.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author DanyGames2014
 */
@Mixin(targets = "net.minecraft.class_277$1")
public class ArmorSlotMixin extends Slot {
    public ArmorSlotMixin(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getBackgroundTextureId() {
        return 7355608;
    }
}
