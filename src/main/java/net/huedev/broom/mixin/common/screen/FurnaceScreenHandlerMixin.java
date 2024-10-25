package net.huedev.broom.mixin.common.screen;

import net.huedev.broom.Broom;
import net.huedev.broom.mixin.common.FuelRegistryAccessor;
import net.huedev.broom.util.ShiftClickFromContainersBehaviorEnum;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SmeltingRecipeManager;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.mixin.recipe.SmeltingRecipeManagerAccessor;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FurnaceScreenHandler.class)
public abstract class FurnaceScreenHandlerMixin extends ScreenHandler {
    @Override
    public ItemStack quickMove(int slotIndex) {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.slots.get(slotIndex);
        if (var3 != null && var3.hasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();
            if (slotIndex == 2) {
                this.insertItem(var4, 3, 39, true);
            } else if (slotIndex >= 3 && slotIndex < 30) {
                boolean hasFuelTag = false;
                for (TagKey<Item> key : FuelRegistryAccessor.broom_getFuelTags().keySet()) {
                    if (var4.isIn(key)) {
                        hasFuelTag = true;
                        break;
                    }
                }
                if (FuelRegistryAccessor.broom_getFuelItems().containsKey(var4.getItem()) || hasFuelTag) {
                    this.insertItem(var4, 1, 2, false);
                } else if (((SmeltingRecipeManagerAccessor) SmeltingRecipeManager.getInstance()).getRecipes().containsKey(var4.itemId)) {
                    this.insertItem(var4, 0, 1, false);
                } else {
                    this.insertItem(var4, 30, 39, false);
                }
            } else if (slotIndex >= 30 && slotIndex < 39) {
                boolean hasFuelTag = false;
                for (TagKey<Item> key : FuelRegistryAccessor.broom_getFuelTags().keySet()) {
                    if (var4.isIn(key)) {
                        hasFuelTag = true;
                        break;
                    }
                }
                if (FuelRegistryAccessor.broom_getFuelItems().containsKey(var4.getItem()) || hasFuelTag) {
                    this.insertItem(var4, 1, 2, false);
                } else if (((SmeltingRecipeManagerAccessor) SmeltingRecipeManager.getInstance()).getRecipes().containsKey(var4.itemId)) {
                    this.insertItem(var4, 0, 1, false);
                } else {
                    this.insertItem(var4, 3, 30, false);
                }
            } else {
                if (!Broom.config.shiftClickOutOfContainersBehavior) {
                    this.insertItem(var4, 3, 39, true);
                } else {
                    this.insertItem(var4, 3, 39, false);
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
