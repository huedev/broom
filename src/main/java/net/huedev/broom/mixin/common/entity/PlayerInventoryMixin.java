package net.huedev.broom.mixin.common.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DanyGames2014
 */
@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Shadow
    public PlayerEntity player;

    @Shadow
    public ItemStack[] main;

    @Shadow
    public int selectedSlot;

    @Unique
    public int broom_getEmptyHotbarSlot() {
        for (int i = 0; i < 9; i++) {
            if (main[i] == null) {
                return i;
            }
        }
        return -1;
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "method_691", at = @At("HEAD"), cancellable = true)
    public void broom_setSelectedItem(int itemId, boolean bl, CallbackInfo ci) {
        int slotWithItemIndex = -1;
        int hotbarSlotIndex;

        for (int i = 0; i < main.length; i++) {
            if (main[i] != null && main[i].itemId == itemId) {
                slotWithItemIndex = i;
                break;
            }
        }

        if (slotWithItemIndex == -1) {
            ci.cancel();
            return;
        }

        if (slotWithItemIndex < 9) {
            selectedSlot = slotWithItemIndex;
        } else {
            if (player.inventory.getSelectedItem() == null) {
                hotbarSlotIndex = this.selectedSlot;
            } else {
                hotbarSlotIndex = broom_getEmptyHotbarSlot();
            }

            if (!player.world.isRemote) {
                if (hotbarSlotIndex != -1) {
                    selectedSlot = hotbarSlotIndex;
                    main[hotbarSlotIndex] = main[slotWithItemIndex];
                    main[slotWithItemIndex] = null;
                } else {
                    ItemStack tempItem = player.getHand();
                    main[selectedSlot] = main[slotWithItemIndex];
                    main[slotWithItemIndex] = tempItem;
                }
            } else {
                if (hotbarSlotIndex != -1) {
                    selectedSlot = hotbarSlotIndex;
                }
            }
        }
    }
}
