package net.huedev.broom.init;

import net.danygames2014.spawneggs.SpawnEggs;
import net.glasslauncher.mods.alwaysmoreitems.api.*;
import net.huedev.broom.Broom;
import net.huedev.broom.block.BroomBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.Identifier;

public class AMIPlugin implements ModPluginProvider {
    @Override
    public String getName() {
        return "broom";
    }

    @Override
    public Identifier getId() {
        return Broom.NAMESPACE.id("broom");
    }

    @Override
    public void onAMIHelpersAvailable(AMIHelpers amiHelpers) {
        ItemBlacklist itemBlacklist = amiHelpers.getItemBlacklist();
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.SAPLING));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.FLOWING_WATER));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.WATER));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.FLOWING_LAVA));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.LAVA));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.LOG));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.LEAVES));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.GRASS));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.BED));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.PISTON_HEAD));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.MOVING_PISTON));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.DOUBLE_SLAB));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.SLAB));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.SLAB, 1, 1));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.SLAB, 1, 2));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.SLAB, 1, 3));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.FIRE));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.REDSTONE_WIRE));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.WHEAT));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.LIT_FURNACE));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.SIGN));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.DOOR));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.WALL_SIGN));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.IRON_DOOR));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.LIT_REDSTONE_ORE));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.LIT_REDSTONE_TORCH));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.SUGAR_CANE));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.REPEATER));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.POWERED_REPEATER));
        itemBlacklist.addItemToBlacklist(new ItemStack(Block.LOCKED_CHEST));

        itemBlacklist.addItemToBlacklist(new ItemStack(BroomBlocks.PUMPKIN_CROPS));
        itemBlacklist.addItemToBlacklist(new ItemStack(BroomBlocks.STONE_DOUBLE_SLAB));
        itemBlacklist.addItemToBlacklist(new ItemStack(BroomBlocks.POLISHED_STONE_DOUBLE_SLAB));
        itemBlacklist.addItemToBlacklist(new ItemStack(BroomBlocks.SANDSTONE_DOUBLE_SLAB));
        itemBlacklist.addItemToBlacklist(new ItemStack(BroomBlocks.WOODEN_DOUBLE_SLAB));
        itemBlacklist.addItemToBlacklist(new ItemStack(BroomBlocks.COBBLESTONE_DOUBLE_SLAB));
        itemBlacklist.addItemToBlacklist(new ItemStack(BroomBlocks.BRICK_DOUBLE_SLAB));
        itemBlacklist.addItemToBlacklist(new ItemStack(BroomBlocks.POLISHED_STONE_BRICK_DOUBLE_SLAB));
        itemBlacklist.addItemToBlacklist(new ItemStack(BroomBlocks.SANDSTONE_BRICK_DOUBLE_SLAB));
        itemBlacklist.addItemToBlacklist(new ItemStack(BroomBlocks.SNOW_BRICK_DOUBLE_SLAB));

        itemBlacklist.addItemToBlacklist(new ItemStack(SpawnEggs.devSword));
    }

    @Override
    public void onItemRegistryAvailable(ItemRegistry itemRegistry) {

    }

    @Override
    public void register(ModRegistry registry) {

    }

    @Override
    public void onRecipeRegistryAvailable(RecipeRegistry recipeRegistry) {

    }

    @Override
    public SyncableRecipe deserializeRecipe(NbtCompound recipe) {
        return null;
    }
}
