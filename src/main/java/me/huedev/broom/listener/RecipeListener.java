package me.huedev.broom.listener;

import me.huedev.broom.item.BroomItemTags;
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
                // Craft all logs into planks
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.PLANKS.asItem(), 4, 0), "X", 'X', BroomItemTags.LOGS);

                // Slab crafting recipes give 6 Slabs
                CraftingHelper.removeRecipe(Block.SLAB.asItem());
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.SLAB.asItem(), 6, 0), "XXX", 'X', new ItemStack(Block.STONE.asItem(), 1));
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.SLAB.asItem(), 6, 1), "XXX", 'X', new ItemStack(Block.SANDSTONE.asItem(), 1));
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.SLAB.asItem(), 6, 2), "XXX", 'X', new ItemStack(Block.PLANKS.asItem(), 1));
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.SLAB.asItem(), 6, 3), "XXX", 'X', new ItemStack(Block.COBBLESTONE.asItem(), 1));

                // Stairs crafting recipes give 8 Stairs
                CraftingHelper.removeRecipe(Block.WOODEN_STAIRS.asItem(), true);
                CraftingHelper.removeRecipe(Block.COBBLESTONE_STAIRS.asItem(), true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.WOODEN_STAIRS.asItem(), 8), "X  ", "XX ", "XXX", 'X', new ItemStack(Block.PLANKS.asItem(), 1));
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.COBBLESTONE_STAIRS.asItem(), 8), "X  ", "XX ", "XXX", 'X', new ItemStack(Block.COBBLESTONE.asItem(), 1));

                // Ladder crafting recipe gives 3 Ladders
                CraftingHelper.removeRecipe(Block.LADDER.asItem(), true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.LADDER.asItem(), 3), "X X", "XXX", "X X", 'X', new ItemStack(Item.STICK, 1));

                // Powered Rails crafting recipe gives 16 Powered Rails
                CraftingHelper.removeRecipe(Block.POWERED_RAIL.asItem(), true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.POWERED_RAIL.asItem(), 16), "X X", "XYX", "XZX", 'X', new ItemStack(Item.GOLD_INGOT, 1), 'Y', new ItemStack(Item.STICK, 1), 'Z', new ItemStack(Item.REDSTONE, 1));

                // Button crafting recipe takes 1 Stone
                CraftingHelper.removeRecipe(Block.BUTTON.asItem(), true);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.BUTTON.asItem(), 1), "X", 'X', new ItemStack(Block.STONE.asItem(), 1));

                // Snow recipe
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.SNOW.asItem(), 6), "XXX", 'X', new ItemStack(Block.SNOW_BLOCK, 1));
            }

            case CRAFTING_SHAPELESS -> {

            }

            case SMELTING -> {
                // Allow smelting all logs into charcoal
                SmeltingRegistry.addSmeltingRecipe(BroomItemTags.LOGS, new ItemStack(Item.COAL, 1, 1));

                // Allow smelting all ore blocks
                SmeltingRegistry.addSmeltingRecipe(Block.COAL_ORE.id, new ItemStack(Item.COAL, 1, 0));
                SmeltingRegistry.addSmeltingRecipe(Block.REDSTONE_ORE.id, new ItemStack(Item.REDSTONE, 1, 0));
                SmeltingRegistry.addSmeltingRecipe(Block.LAPIS_ORE.id, new ItemStack(Item.DYE, 1, 4));
                SmeltingRegistry.addSmeltingRecipe(Block.DIAMOND_ORE.id, new ItemStack(Item.DIAMOND, 1, 0));

                // 60 second fuel duration items (6 items)
                FuelRegistry.addFuelItem(Item.BOAT, 1200);

                // 15 second fuel duration items (1.5 items)
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

                // 5 second fuel duration items (0.5 items)
                FuelRegistry.addFuelTag(BroomItemTags.SAPLINGS, 100);
                FuelRegistry.addFuelItem(Item.BOWL, 100);
                FuelRegistry.addFuelItem(Block.WOOL.asItem(), 100);
                FuelRegistry.addFuelItem(Block.DEAD_BUSH.asItem(), 100);
            }
        }
    }
}
