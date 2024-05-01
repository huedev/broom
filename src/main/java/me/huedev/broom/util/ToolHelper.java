package me.huedev.broom.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.modificationstation.stationapi.api.item.tool.StationSwordItem;

public class ToolHelper {
    public static boolean isUsingItem(PlayerEntity player) {
        return player != null &&
                player.inventory != null &&
                player.inventory.getSelectedItem() != null;
    }

    public static boolean isItemOfToolMaterial(ItemStack stack, ToolMaterial material) {
        Item item = stack.getItem();
        if (item instanceof ToolItem) {
            ToolMaterial toolMaterial = ((ToolItem) item).getMaterial(stack);
            return toolMaterial == material;
        } else if (item instanceof HoeItem) {
            ToolMaterial toolMaterial = ((HoeItem) item).getMaterial(stack);
            return toolMaterial == material;
        }
        return false;
    }

    public static ToolMaterial getItemToolMaterial(PlayerEntity player) {
        ItemStack stack = player.inventory.getSelectedItem();
        if (stack == null) {
            return null;
        }

        Item item = stack.getItem();
        if (item instanceof ToolItem) {
            return ((ToolItem) item).getMaterial(stack);
        } else if (item instanceof HoeItem) {
            return ((HoeItem) item).getMaterial(stack);
        }
        return null;
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

    public static boolean isUsingWoodTool(PlayerEntity player) {
        ItemStack stack = player.inventory.getSelectedItem();
        if (stack == null) {
            return false;
        }

        return isUsingItem(player) && isItemOfToolMaterial(stack, ToolMaterial.WOOD);
    }

    public static boolean isUsingStoneTool(PlayerEntity player) {
        ItemStack stack = player.inventory.getSelectedItem();
        if (stack == null) {
            return false;
        }

        return isUsingItem(player) && isItemOfToolMaterial(stack, ToolMaterial.STONE);
    }

    public static boolean isUsingIronTool(PlayerEntity player) {
        ItemStack stack = player.inventory.getSelectedItem();
        if (stack == null) {
            return false;
        }

        return isUsingItem(player) && isItemOfToolMaterial(stack, ToolMaterial.IRON);
    }

    public static boolean isUsingGoldenTool(PlayerEntity player) {
        ItemStack stack = player.inventory.getSelectedItem();
        if (stack == null) {
            return false;
        }

        return isUsingItem(player) && isItemOfToolMaterial(stack, ToolMaterial.GOLD);
    }

    public static boolean isUsingDiamondTool(PlayerEntity player) {
        ItemStack stack = player.inventory.getSelectedItem();
        if (stack == null) {
            return false;
        }

        return isUsingItem(player) && isItemOfToolMaterial(stack, ToolMaterial.DIAMOND);
    }
}
