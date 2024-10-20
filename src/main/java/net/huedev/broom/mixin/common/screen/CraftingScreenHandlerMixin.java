package net.huedev.broom.mixin.common.screen;

import net.huedev.broom.Broom;
import net.huedev.broom.util.ShiftClickFromContainersBehaviorEnum;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CraftingScreenHandler.class)
public abstract class CraftingScreenHandlerMixin extends ScreenHandler {
    @Override
    public ItemStack quickMove(int slotIndex) {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.slots.get(slotIndex);
        if (var3 != null && var3.hasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();
            if (slotIndex == 0) {
                this.insertItem(var4, 10, 46, true);
            } else if (slotIndex >= 10 && slotIndex < 37) {
                this.insertItem(var4, 1, 10, false);
            } else if (slotIndex >= 37 && slotIndex < 46) {
                this.insertItem(var4, 1, 37, false);
            } else {
                if (!Broom.config.shiftClickOutOfContainersBehavior) {
                    this.insertItem(var4, 10, 46, true);
                } else {
                    this.insertItem(var4, 10, 46, false);
                }
            }

            if (var4.count == 0) {
                var3.setStack((ItemStack)null);
            } else {
                var3.markDirty();
            }

            if (var4.count == var2.count) {
                return null;
            }

            var3.onTakeItem(var4);
        }

        return var2;
    }
}
