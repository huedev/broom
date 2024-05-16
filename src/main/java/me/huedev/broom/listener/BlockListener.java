package me.huedev.broom.listener;

import me.huedev.broom.block.BroomBlockTags;
import me.huedev.broom.block.BroomBlocks;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.event.block.FireBurnableRegisterEvent;
import net.modificationstation.stationapi.api.event.block.IsBlockReplaceableEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;

@SuppressWarnings("unused")
public class BlockListener {
    @EventListener
    private void registerBlocks(BlockRegistryEvent event) {
        // Vanilla block fixes
        event.registry.getEntry(Block.GRASS_BLOCK).value().setTranslationKey("grass_block");
        event.registry.getEntry(Block.COBBLESTONE).value().setTranslationKey("cobblestone");
        event.registry.getEntry(Block.PLANKS).value().setTranslationKey("wooden_planks");
        event.registry.getEntry(Block.GOLD_ORE).value().setTranslationKey("gold_ore");
        event.registry.getEntry(Block.IRON_ORE).value().setTranslationKey("iron_ore");
        event.registry.getEntry(Block.COAL_ORE).value().setTranslationKey("coal_ore");
        event.registry.getEntry(Block.LOG).value().setTranslationKey("log");
        event.registry.getEntry(Block.LAPIS_ORE).value().setTranslationKey("lapis_ore");
        event.registry.getEntry(Block.LAPIS_BLOCK).value().setTranslationKey("lapis_block");
        event.registry.getEntry(Block.SANDSTONE).value().setTranslationKey("sandstone");
        event.registry.getEntry(Block.NOTE_BLOCK).value().setTranslationKey("note_block").setSoundGroup(Block.WOOD_SOUND_GROUP);
        event.registry.getEntry(Block.BED).value().setSoundGroup(Block.WOOD_SOUND_GROUP);
        event.registry.getEntry(Block.POWERED_RAIL).value().setTranslationKey("powered_rail");
        event.registry.getEntry(Block.DETECTOR_RAIL).value().setTranslationKey("detector_rail");
        event.registry.getEntry(Block.STICKY_PISTON).value().setTranslationKey("sticky_piston");
        event.registry.getEntry(Block.COBWEB).value().setTranslationKey("cobweb").setSoundGroup(Block.WOOL_SOUND_GROUP);
        event.registry.getEntry(Block.GRASS).value().setTranslationKey("grass");
        event.registry.getEntry(Block.DEAD_BUSH).value().setTranslationKey("dead_bush");
        event.registry.getEntry(Block.PISTON).value().setTranslationKey("piston");
        event.registry.getEntry(Block.WOOL).value().setTranslationKey("wool");
        event.registry.getEntry(Block.DANDELION).value().setTranslationKey("dandelion");
        event.registry.getEntry(Block.BROWN_MUSHROOM).value().setTranslationKey("brown_mushroom");
        event.registry.getEntry(Block.RED_MUSHROOM).value().setTranslationKey("red_mushroom");
        event.registry.getEntry(Block.GOLD_BLOCK).value().setTranslationKey("gold_block");
        event.registry.getEntry(Block.IRON_BLOCK).value().setTranslationKey("iron_block");
        event.registry.getEntry(Block.DOUBLE_SLAB).value().setTranslationKey("slab");
        event.registry.getEntry(Block.SLAB).value().setTranslationKey("slab");
        event.registry.getEntry(Block.BRICKS).value().setTranslationKey("bricks");
        event.registry.getEntry(Block.MOSSY_COBBLESTONE).value().setTranslationKey("mossy_cobblestone");
        event.registry.getEntry(Block.OBSIDIAN).value().setHardness(20.0F);
        event.registry.getEntry(Block.SPAWNER).value().setTranslationKey("spawner");
        event.registry.getEntry(Block.WOODEN_STAIRS).value().setTranslationKey("wooden_stairs");
        event.registry.getEntry(Block.REDSTONE_WIRE).value().setTranslationKey("redstone_wire");
        event.registry.getEntry(Block.DIAMOND_ORE).value().setTranslationKey("diamond_ore");
        event.registry.getEntry(Block.DIAMOND_BLOCK).value().setTranslationKey("diamond_block");
        event.registry.getEntry(Block.CRAFTING_TABLE).value().setTranslationKey("crafting_table");
        event.registry.getEntry(Block.WHEAT).value().setTranslationKey("wheat");
        event.registry.getEntry(Block.DOOR).value().setTranslationKey("wooden_door");
        event.registry.getEntry(Block.COBBLESTONE_STAIRS).value().setTranslationKey("cobblestone_stairs");
        event.registry.getEntry(Block.STONE_PRESSURE_PLATE).value().setTranslationKey("stone_pressure_plate");
        event.registry.getEntry(Block.IRON_DOOR).value().setTranslationKey("iron_door");
        event.registry.getEntry(Block.WOODEN_PRESSURE_PLATE).value().setTranslationKey("wooden_pressure_plate");
        event.registry.getEntry(Block.REDSTONE_ORE).value().setTranslationKey("redstone_ore");
        event.registry.getEntry(Block.LIT_REDSTONE_ORE).value().setTranslationKey("redstone_ore");
        event.registry.getEntry(Block.REDSTONE_TORCH).value().setTranslationKey("redstone_torch");
        event.registry.getEntry(Block.LIT_REDSTONE_TORCH).value().setTranslationKey("redstone_torch");
        event.registry.getEntry(Block.BUTTON).value().setTranslationKey("stone_button");
        event.registry.getEntry(Block.SNOW_BLOCK).value().setTranslationKey("snow_block");
        event.registry.getEntry(Block.SUGAR_CANE).value().setTranslationKey("sugar_cane");
        event.registry.getEntry(Block.JUKEBOX).value().setSoundGroup(Block.WOOD_SOUND_GROUP);
        event.registry.getEntry(Block.PUMPKIN).value().setTranslationKey("carved_pumpkin");
        event.registry.getEntry(Block.NETHERRACK).value().setTranslationKey("netherrack");
        event.registry.getEntry(Block.SOUL_SAND).value().setTranslationKey("soul_sand");
        event.registry.getEntry(Block.GLOWSTONE).value().setTranslationKey("glowstone");
        event.registry.getEntry(Block.NETHER_PORTAL).value().setTranslationKey("nether_portal");
        event.registry.getEntry(Block.JACK_O_LANTERN).value().setTranslationKey("jack_o_lantern");
        event.registry.getEntry(Block.SUGAR_CANE).value().setTranslationKey("sugar_cane");
        event.registry.getEntry(Block.REPEATER).value().setTranslationKey("repeater").setSoundGroup(Block.STONE_SOUND_GROUP);
        event.registry.getEntry(Block.POWERED_REPEATER).value().setTranslationKey("repeater").setSoundGroup(Block.STONE_SOUND_GROUP);
        event.registry.getEntry(Block.LOCKED_CHEST).value().setTranslationKey("locked_chest");
        event.registry.getEntry(Block.TRAPDOOR).value().setTranslationKey("wooden_trapdoor");

        BroomBlocks.init();
    }

    @EventListener
    private void registerBurnableBlocks(FireBurnableRegisterEvent event) {
        event.addBurnable(BroomBlocks.OAK_LOG.id, 5, 5);
        event.addBurnable(BroomBlocks.SPRUCE_LOG.id, 5, 5);
        event.addBurnable(BroomBlocks.BIRCH_LOG.id, 5, 5);
        event.addBurnable(BroomBlocks.CACAO_LOG.id, 5, 5);

        event.addBurnable(BroomBlocks.OAK_BARK.id, 5, 5);
        event.addBurnable(BroomBlocks.SPRUCE_BARK.id, 5, 5);
        event.addBurnable(BroomBlocks.BIRCH_BARK.id, 5, 5);
        event.addBurnable(BroomBlocks.CACAO_BARK.id, 5, 5);

        event.addBurnable(BroomBlocks.OAK_LEAVES.id, 30, 60);
        event.addBurnable(BroomBlocks.APPLE_OAK_LEAVES.id, 30, 60);
        event.addBurnable(BroomBlocks.SPRUCE_LEAVES.id, 30, 60);
        event.addBurnable(BroomBlocks.BIRCH_LEAVES.id, 30, 60);

        event.addBurnable(BroomBlocks.WOODEN_SLAB.id, 5, 20);
        event.addBurnable(BroomBlocks.WOODEN_DOUBLE_SLAB.id, 5, 20);
        event.addBurnable(BroomBlocks.FENCE_GATE.id, 5, 20);

        event.addBurnable(BroomBlocks.COAL_BLOCK.id, 5, 5);
        event.addBurnable(BroomBlocks.CHARCOAL_BLOCK.id, 5, 5);
    }
}
