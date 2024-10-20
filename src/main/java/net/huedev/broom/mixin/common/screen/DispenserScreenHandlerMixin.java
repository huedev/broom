package net.huedev.broom.mixin.common.screen;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.DispenserScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DispenserScreenHandler.class)
public abstract class DispenserScreenHandlerMixin extends ScreenHandler {
    @Override
    public ItemStack quickMove(int slotIndex) {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.slots.get(slotIndex);
        if (var3 != null && var3.hasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();
            if (slotIndex < 9) {
                this.insertItem(var4, 9, this.slots.size(), true);
            } else {
                this.insertItem(var4, 0, 9, false);
            }

            if (var4.count == 0) {
                var3.setStack((ItemStack)null);
            } else {
                var3.markDirty();
            }
        }

        return var2;
    }
}
