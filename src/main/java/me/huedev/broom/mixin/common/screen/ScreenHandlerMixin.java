package me.huedev.broom.mixin.common.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {
    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void broom_onSlotClick(int slotIndex, int button, boolean shift, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        ScreenHandler screenHandler = (ScreenHandler)(Object)this;
        ItemStack var5 = null;
        if (button == 0 || button == 1) {
            PlayerInventory var6 = player.inventory;
            if (slotIndex == -999) {
                if (var6.getCursorStack() != null && slotIndex == -999) {
                    if (button == 0) {
                        player.dropItem(var6.getCursorStack());
                        var6.setCursorStack((ItemStack)null);
                    }

                    if (button == 1) {
                        player.dropItem(var6.getCursorStack().split(1));
                        if (var6.getCursorStack().count == 0) {
                            var6.setCursorStack((ItemStack)null);
                        }
                    }
                }
            } else {
                int var10;
                if (shift) {
                    if ((player.container instanceof CraftingScreenHandler || player.container instanceof PlayerScreenHandler) && slotIndex == 0) {
                        // To prevent shift-crafting multiple different outputs,
                        // need to determine what the desired output is before the loop
                        Slot var2 = (Slot)screenHandler.slots.get(slotIndex);
                        ItemStack var7 = var2 != null ? var2.getStack() : null;
                        int originalOutput;
                        if (var7 == null) {
                            originalOutput = -1;
                        } else {
                            originalOutput = var7.itemId;
                        }
                        do {
                            // At start of loop, need to determine what the current output is,
                            // that way we can stop if it's not the original output
                            var2 = (Slot)screenHandler.slots.get(slotIndex);
                            var7 = var2 != null ? var2.getStack() : null;
                            if (var7 != null && var7.itemId == originalOutput) {
                                // Now that we know that the output is what we originally desired,
                                // process the stack like normal
                                var7 = screenHandler.getStackInSlot(slotIndex);
                                if (var7 != null && var7.itemId == originalOutput) {
                                    int var8 = var7.count;
                                    var5 = var7.copy();
                                    Slot var9 = (Slot) screenHandler.slots.get(slotIndex);
                                    if (var9 != null && var9.getStack() != null) {
                                        var10 = var9.getStack().count;
                                        if (var10 < var8 && var9.getStack().itemId == originalOutput) {
                                            // Check if the remaining output is what we originally desired
                                            // before we recursively call to process it
                                            screenHandler.onSlotClick(slotIndex, button, shift, player);
                                        }
                                    }
                                }
                            }
                        } while (var7 != null && var7.itemId == originalOutput);
                    } else {
                        ItemStack var7 = screenHandler.getStackInSlot(slotIndex);
                        if (var7 != null) {
                            int var8 = var7.count;
                            var5 = var7.copy();
                            Slot var9 = (Slot)screenHandler.slots.get(slotIndex);
                            if (var9 != null && var9.getStack() != null) {
                                var10 = var9.getStack().count;
                                if (var10 < var8) {
                                    screenHandler.onSlotClick(slotIndex, button, shift, player);
                                }
                            }
                        }
                    }
                } else {
                    Slot var12 = (Slot)screenHandler.slots.get(slotIndex);
                    if (var12 != null) {
                        var12.markDirty();
                        ItemStack var13 = var12.getStack();
                        ItemStack var14 = var6.getCursorStack();
                        if (var13 != null) {
                            var5 = var13.copy();
                        }

                        if (var13 == null) {
                            if (var14 != null && var12.canInsert(var14)) {
                                var10 = button == 0 ? var14.count : 1;
                                if (var10 > var12.getMaxItemCount()) {
                                    var10 = var12.getMaxItemCount();
                                }

                                var12.setStack(var14.split(var10));
                                if (var14.count == 0) {
                                    var6.setCursorStack((ItemStack)null);
                                }
                            }
                        } else if (var14 == null) {
                            var10 = button == 0 ? var13.count : (var13.count + 1) / 2;
                            ItemStack var11 = var12.takeStack(var10);
                            var6.setCursorStack(var11);
                            if (var13.count == 0) {
                                var12.setStack((ItemStack)null);
                            }

                            var12.onCrafted(var6.getCursorStack());
                        } else if (var12.canInsert(var14)) {
                            if (var13.itemId == var14.itemId && (!var13.hasSubtypes() || var13.getDamage() == var14.getDamage())) {
                                var10 = button == 0 ? var14.count : 1;
                                if (var10 > var12.getMaxItemCount() - var13.count) {
                                    var10 = var12.getMaxItemCount() - var13.count;
                                }

                                if (var10 > var14.getMaxCount() - var13.count) {
                                    var10 = var14.getMaxCount() - var13.count;
                                }

                                var14.split(var10);
                                if (var14.count == 0) {
                                    var6.setCursorStack((ItemStack)null);
                                }

                                var13.count += var10;
                            } else if (var14.count <= var12.getMaxItemCount()) {
                                var12.setStack(var14);
                                var6.setCursorStack(var13);
                            }
                        } else if (var13.itemId == var14.itemId && var14.getMaxCount() > 1 && (!var13.hasSubtypes() || var13.getDamage() == var14.getDamage())) {
                            var10 = var13.count;
                            if (var10 > 0 && var10 + var14.count <= var14.getMaxCount()) {
                                var14.count += var10;
                                var13.split(var10);
                                if (var13.count == 0) {
                                    var12.setStack((ItemStack)null);
                                }

                                var12.onCrafted(var6.getCursorStack());
                            }
                        }
                    }
                }
            }
        }

        cir.setReturnValue(var5);
    }
}
