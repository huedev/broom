package me.huedev.broom.listener;

import me.huedev.broom.item.BroomItems;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;

@SuppressWarnings("unused")
public class ItemListener {
    @EventListener
    private void registerItems(ItemRegistryEvent event) {
        // Vanilla item fixes
        event.registry.getEntry(Item.IRON_SHOVEL).value().setTranslationKey("iron_shovel");
        event.registry.getEntry(Item.IRON_PICKAXE).value().setTranslationKey("iron_pickaxe");
        event.registry.getEntry(Item.IRON_AXE).value().setTranslationKey("iron_axe");
        event.registry.getEntry(Item.FLINT_AND_STEEL).value().setTranslationKey("flint_and_steel");
        event.registry.getEntry(Item.DIAMOND).value().setTranslationKey("diamond");
        event.registry.getEntry(Item.IRON_INGOT).value().setTranslationKey("iron_ingot");
        event.registry.getEntry(Item.GOLD_INGOT).value().setTranslationKey("gold_ingot");
        event.registry.getEntry(Item.IRON_SWORD).value().setTranslationKey("iron_sword");
        event.registry.getEntry(Item.WOODEN_SWORD).value().setTranslationKey("wooden_sword");
        event.registry.getEntry(Item.WOODEN_SHOVEL).value().setTranslationKey("wooden_shovel");
        event.registry.getEntry(Item.WOODEN_PICKAXE).value().setTranslationKey("wooden_pickaxe");
        event.registry.getEntry(Item.WOODEN_AXE).value().setTranslationKey("wooden_axe");
        event.registry.getEntry(Item.STONE_SWORD).value().setTranslationKey("stone_sword");
        event.registry.getEntry(Item.STONE_SHOVEL).value().setTranslationKey("stone_shovel");
        event.registry.getEntry(Item.STONE_PICKAXE).value().setTranslationKey("stone_pickaxe");
        event.registry.getEntry(Item.STONE_HATCHET).value().setTranslationKey("stone_axe");
        event.registry.getEntry(Item.DIAMOND_SWORD).value().setTranslationKey("diamond_sword");
        event.registry.getEntry(Item.DIAMOND_SHOVEL).value().setTranslationKey("diamond_shovel");
        event.registry.getEntry(Item.DIAMOND_PICKAXE).value().setTranslationKey("diamond_pickaxe");
        event.registry.getEntry(Item.DIAMOND_AXE).value().setTranslationKey("diamond_axe");
        event.registry.getEntry(Item.MUSHROOM_STEW).value().setTranslationKey("mushroom_stew");
        event.registry.getEntry(Item.GOLDEN_SWORD).value().setTranslationKey("golden_sword");
        event.registry.getEntry(Item.GOLDEN_SHOVEL).value().setTranslationKey("golden_shovel");
        event.registry.getEntry(Item.GOLDEN_PICKAXE).value().setTranslationKey("golden_pickaxe");
        event.registry.getEntry(Item.GOLDEN_AXE).value().setTranslationKey("golden_axe");
        event.registry.getEntry(Item.GUNPOWDER).value().setTranslationKey("gunpowder");
        event.registry.getEntry(Item.WOODEN_HOE).value().setTranslationKey("wooden_hoe");
        event.registry.getEntry(Item.STONE_HOE).value().setTranslationKey("stone_hoe");
        event.registry.getEntry(Item.IRON_HOE).value().setTranslationKey("iron_hoe");
        event.registry.getEntry(Item.DIAMOND_HOE).value().setTranslationKey("diamond_hoe");
        event.registry.getEntry(Item.GOLDEN_HOE).value().setTranslationKey("golden_hoe");
        event.registry.getEntry(Item.SEEDS).value().setTranslationKey("wheat_seeds");
        event.registry.getEntry(Item.LEATHER_HELMET).value().setTranslationKey("leather_helmet");
        event.registry.getEntry(Item.LEATHER_CHESTPLATE).value().setTranslationKey("leather_chestplate");
        event.registry.getEntry(Item.LEATHER_LEGGINGS).value().setTranslationKey("leather_leggings");
        event.registry.getEntry(Item.LEATHER_BOOTS).value().setTranslationKey("leather_boots");
        event.registry.getEntry(Item.CHAIN_HELMET).value().setTranslationKey("chainmail_helmet");
        event.registry.getEntry(Item.CHAIN_CHESTPLATE).value().setTranslationKey("chainmail_chestplate");
        event.registry.getEntry(Item.CHAIN_LEGGINGS).value().setTranslationKey("chainmail_leggings");
        event.registry.getEntry(Item.CHAIN_BOOTS).value().setTranslationKey("chainmail_boots");
        event.registry.getEntry(Item.IRON_HELMET).value().setTranslationKey("iron_helmet");
        event.registry.getEntry(Item.IRON_CHESTPLATE).value().setTranslationKey("iron_chestplate");
        event.registry.getEntry(Item.IRON_LEGGINGS).value().setTranslationKey("iron_leggings");
        event.registry.getEntry(Item.IRON_BOOTS).value().setTranslationKey("iron_boots");
        event.registry.getEntry(Item.DIAMOND_HELMET).value().setTranslationKey("diamond_helmet");
        event.registry.getEntry(Item.DIAMOND_CHESTPLATE).value().setTranslationKey("diamond_chestplate");
        event.registry.getEntry(Item.DIAMOND_LEGGINGS).value().setTranslationKey("diamond_leggings");
        event.registry.getEntry(Item.DIAMOND_BOOTS).value().setTranslationKey("diamond_boots");
        event.registry.getEntry(Item.GOLDEN_HELMET).value().setTranslationKey("golden_helmet");
        event.registry.getEntry(Item.GOLDEN_CHESTPLATE).value().setTranslationKey("golden_chestplate");
        event.registry.getEntry(Item.GOLDEN_LEGGINGS).value().setTranslationKey("golden_leggings");
        event.registry.getEntry(Item.GOLDEN_BOOTS).value().setTranslationKey("golden_boots");
        event.registry.getEntry(Item.RAW_PORKCHOP).value().setTranslationKey("porkchop");
        event.registry.getEntry(Item.COOKED_PORKCHOP).value().setTranslationKey("cooked_porkchop");
        event.registry.getEntry(Item.GOLDEN_APPLE).value().setTranslationKey("golden_apple");
        event.registry.getEntry(Item.SIGN).value().setMaxCount(64);
        event.registry.getEntry(Item.WOODEN_DOOR).value().setTranslationKey("wooden_door").setMaxCount(64);
        event.registry.getEntry(Item.WATER_BUCKET).value().setTranslationKey("water_bucket");
        event.registry.getEntry(Item.LAVA_BUCKET).value().setTranslationKey("lava_bucket");
        event.registry.getEntry(Item.IRON_DOOR).value().setTranslationKey("iron_door").setMaxCount(64);
        event.registry.getEntry(Item.SNOWBALL).value().setMaxCount(64);
        event.registry.getEntry(Item.MILK_BUCKET).value().setTranslationKey("milk_bucket");
        event.registry.getEntry(Item.CLAY).value().setTranslationKey("clay_ball");
        event.registry.getEntry(Item.SUGAR_CANE).value().setTranslationKey("sugar_cane");
        event.registry.getEntry(Item.CHEST_MINECART).value().setTranslationKey("chest_minecart");
        event.registry.getEntry(Item.FURNACE_MINECART).value().setTranslationKey("furnace_minecart");
        event.registry.getEntry(Item.EGG).value().setMaxCount(64);
        event.registry.getEntry(Item.FISHING_ROD).value().setTranslationKey("fishing_rod");
        event.registry.getEntry(Item.RAW_FISH).value().setTranslationKey("fish");
        event.registry.getEntry(Item.COOKED_FISH).value().setTranslationKey("cooked_fish");
        event.registry.getEntry(Item.DYE).value().setTranslationKey("dye");
        event.registry.getEntry(Item.REPEATER).value().setTranslationKey("repeater");
        event.registry.getEntry(Item.RECORD_THIRTEEN).value().setTranslationKey("music_disc");
        event.registry.getEntry(Item.RECORD_CAT).value().setTranslationKey("music_disc");

        BroomItems.init();
    }
}
