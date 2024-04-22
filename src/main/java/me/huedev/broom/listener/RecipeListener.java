package me.huedev.broom.listener;

import me.huedev.broom.block.BroomBlocks;
import me.huedev.broom.item.BroomItemTags;
import me.huedev.broom.item.BroomItems;
import me.huedev.broom.util.CraftingHelper;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.recipe.FuelRegistry;
import net.modificationstation.stationapi.api.recipe.SmeltingRegistry;

@SuppressWarnings("unused")
public class RecipeListener {
    @EventListener
    private void registerRecipes(RecipeRegisterEvent event) {
        RecipeRegisterEvent.Vanilla type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId);

        switch (type != null ? type : RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED) {
            case CRAFTING_SHAPED -> {
                // Remove vanilla slab crafting recipes
                CraftingHelper.removeRecipe(Block.SLAB.asItem());

                // Stairs crafting recipes give 6 Stairs
                CraftingHelper.removeRecipe(Block.WOODEN_STAIRS.asItem(), true);
                CraftingHelper.removeRecipe(Block.COBBLESTONE_STAIRS.asItem(), true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.WOODEN_STAIRS.asItem(), 6), "X  ", "XX ", "XXX", 'X', new ItemStack(Block.PLANKS.asItem(), 1));
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.COBBLESTONE_STAIRS.asItem(), 6), "X  ", "XX ", "XXX", 'X', new ItemStack(Block.COBBLESTONE.asItem(), 1));

                // Ladder crafting recipe gives 3 Ladders
                CraftingHelper.removeRecipe(Block.LADDER.asItem(), true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.LADDER.asItem(), 3), "X X", "XXX", "X X", 'X', new ItemStack(Item.STICK, 1));

                // Sign crafting recipe gives 3 Signs
                CraftingHelper.removeRecipe(Item.SIGN, true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Item.SIGN, 3), "XXX", "XXX", " Y ", 'X', new ItemStack(Block.PLANKS.asItem(), 1), 'Y', new ItemStack(Item.STICK, 1));

                // Wooden Door crafting recipe gives 3 Wooden Doors
                CraftingHelper.removeRecipe(Item.WOODEN_DOOR, true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Item.WOODEN_DOOR, 3), "XX", "XX", "XX", 'X', new ItemStack(Block.PLANKS.asItem(), 1));

                // Iron Door crafting recipe gives 3 Iron Doors
                CraftingHelper.removeRecipe(Item.IRON_DOOR, true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Item.IRON_DOOR, 3), "XX", "XX", "XX", 'X', new ItemStack(Item.IRON_INGOT, 1));

                // Wooden Trapdoor crafting recipe gives 6 Wooden Trapdoors
                CraftingHelper.removeRecipe(Block.TRAPDOOR.asItem(), true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.TRAPDOOR.asItem(), 6), "XXX", "XXX", 'X', new ItemStack(Block.PLANKS.asItem(), 1));

                // Powered Rails crafting recipe gives 16 Powered Rails
                CraftingHelper.removeRecipe(Block.POWERED_RAIL.asItem(), true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.POWERED_RAIL.asItem(), 16), "X X", "XYX", "XZX", 'X', new ItemStack(Item.GOLD_INGOT, 1), 'Y', new ItemStack(Item.STICK, 1), 'Z', new ItemStack(Item.REDSTONE, 1));

                // Button crafting recipe takes 1 Stone
                CraftingHelper.removeRecipe(Block.BUTTON.asItem(), true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.BUTTON.asItem(), 1), "X", 'X', new ItemStack(Block.STONE.asItem(), 1));

                // Golden Apple crafting recipe takes 8 Gold Ingots
                CraftingHelper.removeRecipe(Item.GOLDEN_APPLE, true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Item.GOLDEN_APPLE, 1), "XXX", "XYX", "XXX", 'X', new ItemStack(Item.GOLD_INGOT, 1), 'Y', new ItemStack(Item.APPLE, 1));
            }

            case CRAFTING_SHAPELESS -> {

            }

            case SMELTING -> {
                // 60 second fuel duration items (6 items)
                FuelRegistry.addFuelItem(Item.BOAT, 1200);

                // 15 second fuel duration items (1.5 items)
                FuelRegistry.addFuelItem(BroomBlocks.WOODEN_DOUBLE_SLAB.asItem(), 150);
                FuelRegistry.addFuelItem(Item.BOW, 300);
                FuelRegistry.addFuelItem(Item.FISHING_ROD, 300);
                FuelRegistry.addFuelItem(Block.LADDER.asItem(), 300);

                // 10 second fuel duration items (1 item)
                FuelRegistry.addFuelItem(Item.WOODEN_AXE, 200);
                FuelRegistry.addFuelItem(Item.WOODEN_HOE, 200);
                FuelRegistry.addFuelItem(Item.WOODEN_PICKAXE, 200);
                FuelRegistry.addFuelItem(Item.WOODEN_SHOVEL, 200);
                FuelRegistry.addFuelItem(Item.WOODEN_SWORD, 200);
                FuelRegistry.addFuelItem(Item.SIGN, 200);
                FuelRegistry.addFuelItem(Item.WOODEN_DOOR, 200);

                // 7.5 second fuel duration items (0.75 items)
                FuelRegistry.addFuelItem(BroomBlocks.WOODEN_SLAB.asItem(), 150);

                // 5 second fuel duration items (0.5 items)
                FuelRegistry.addFuelTag(BroomItemTags.SAPLINGS, 100);
                FuelRegistry.addFuelItem(Item.BOWL, 100);
                FuelRegistry.addFuelItem(Block.WOOL.asItem(), 100);
                FuelRegistry.addFuelItem(Block.DEAD_BUSH.asItem(), 100);
            }
        }
    }
}
