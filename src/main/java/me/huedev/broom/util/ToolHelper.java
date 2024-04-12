package me.huedev.broom.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.modificationstation.stationapi.api.item.tool.StationHoeItem;
import net.modificationstation.stationapi.api.item.tool.StationShearsItem;
import net.modificationstation.stationapi.api.item.tool.StationSwordItem;
import net.modificationstation.stationapi.api.item.tool.StationToolItem;

import javax.tools.Tool;

public class ToolHelper {
    public static boolean isUsingItem(PlayerEntity player) {
        return player != null &&
                player.inventory != null &&
                player.inventory.getSelectedItem() != null;
    }

    public static boolean isUsingSword(PlayerEntity player) {
        return isUsingItem(player) && player.inventory.getSelectedItem().getItem() instanceof StationSwordItem;
    }

    public static boolean isUsingShovel(PlayerEntity player) {
        return isUsingItem(player) && player.inventory.getSelectedItem().getItem() instanceof ShovelItem;
    }

    public static boolean isUsingShears(PlayerEntity player) {
        return isUsingItem(player) && player.inventory.getSelectedItem().getItem() instanceof ShearsItem;
    }

    public static boolean isUsingSilkTouchTool(PlayerEntity player) {
        ItemStack stack = player.inventory.getSelectedItem();
        if (stack == null) {
            return false;
        }

        Item item = stack.getItem();
        if (item instanceof ToolItem) {
            ToolMaterial toolMaterial = ((ToolItem) item).getMaterial(stack);
            return isUsingItem(player) && toolMaterial == ToolMaterial.GOLD;
        } else if (item instanceof HoeItem) {
            ToolMaterial toolMaterial = ((HoeItem) item).getMaterial(stack);
            return isUsingItem(player) && toolMaterial == ToolMaterial.GOLD;
        }
        return false;
    }
}
