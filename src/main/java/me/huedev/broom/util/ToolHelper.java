package me.huedev.broom.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.SwordItem;

public class ToolHelper {
    public static boolean isUsingItem(PlayerEntity player) {
        return player != null &&
                player.inventory != null &&
                player.inventory.getSelectedItem() != null;
    }

    public static boolean isUsingSword(PlayerEntity player) {
        return isUsingItem(player) && player.inventory.getSelectedItem().getItem() instanceof SwordItem;
    }

    public static boolean isUsingShears(PlayerEntity player) {
        return isUsingItem(player) && player.inventory.getSelectedItem().getItem() instanceof ShearsItem;
    }
}
