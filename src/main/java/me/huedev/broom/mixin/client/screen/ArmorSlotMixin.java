package me.huedev.broom.mixin.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author DanyGames2014
 */
@Mixin(targets = "net.minecraft.screen.PlayerScreenHandler$1")
public class ArmorSlotMixin extends Slot {
    public ArmorSlotMixin(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int method_471() {
        return 7355608;
    }
}
