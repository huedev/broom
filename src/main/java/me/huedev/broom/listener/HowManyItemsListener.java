package me.huedev.broom.listener;

import me.huedev.broom.block.BroomBlocks;
import net.glasslauncher.hmifabric.Utils;
import net.glasslauncher.hmifabric.event.HMITabRegistryEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class HowManyItemsListener {
    @EventListener
    public void registerTabs(HMITabRegistryEvent event) {
        Utils.hiddenItems.add(new ItemStack(Block.LOG));
        Utils.hiddenItems.add(new ItemStack(Block.LOG, 1, 1));
        Utils.hiddenItems.add(new ItemStack(Block.LOG, 1, 2));
        Utils.hiddenItems.add(new ItemStack(Block.LEAVES));
        Utils.hiddenItems.add(new ItemStack(Block.LEAVES, 1, 1));
        Utils.hiddenItems.add(new ItemStack(Block.LEAVES, 1, 2));
        Utils.hiddenItems.add(new ItemStack(Block.SAPLING));
        Utils.hiddenItems.add(new ItemStack(Block.SAPLING, 1, 1));
        Utils.hiddenItems.add(new ItemStack(Block.SAPLING, 1, 2));
        Utils.hiddenItems.add(new ItemStack(Block.GRASS));
        Utils.hiddenItems.add(new ItemStack(Block.SLAB));
        Utils.hiddenItems.add(new ItemStack(Block.SLAB, 1, 1));
        Utils.hiddenItems.add(new ItemStack(Block.SLAB, 1, 2));
        Utils.hiddenItems.add(new ItemStack(Block.SLAB, 1, 3));

        Utils.hiddenItems.add(new ItemStack(BroomBlocks.PUMPKIN_CROPS));
        Utils.hiddenItems.add(new ItemStack(BroomBlocks.STONE_DOUBLE_SLAB));
        Utils.hiddenItems.add(new ItemStack(BroomBlocks.POLISHED_STONE_DOUBLE_SLAB));
        Utils.hiddenItems.add(new ItemStack(BroomBlocks.SANDSTONE_DOUBLE_SLAB));
        Utils.hiddenItems.add(new ItemStack(BroomBlocks.WOODEN_DOUBLE_SLAB));
        Utils.hiddenItems.add(new ItemStack(BroomBlocks.COBBLESTONE_DOUBLE_SLAB));
        Utils.hiddenItems.add(new ItemStack(BroomBlocks.BRICK_DOUBLE_SLAB));
        Utils.hiddenItems.add(new ItemStack(BroomBlocks.POLISHED_STONE_BRICK_DOUBLE_SLAB));
        Utils.hiddenItems.add(new ItemStack(BroomBlocks.SANDSTONE_BRICK_DOUBLE_SLAB));
        Utils.hiddenItems.add(new ItemStack(BroomBlocks.SNOW_BRICK_DOUBLE_SLAB));
    }
}
