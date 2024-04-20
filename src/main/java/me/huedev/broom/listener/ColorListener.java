package me.huedev.broom.listener;

import me.huedev.broom.block.BroomBlocks;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.class_287;
import net.modificationstation.stationapi.api.client.color.world.BiomeColors;
import net.modificationstation.stationapi.api.client.event.color.block.BlockColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;

/**
 * @author paulevsGitch
 */
@SuppressWarnings("unused")
public class ColorListener {
    @EventListener
    public void registerBlockColors(BlockColorsRegisterEvent event) {
        event.blockColors.registerColorProvider(
                (blockState, blockView, blockPos, index) -> BiomeColors.getFoliageColor(blockView, blockPos),
                BroomBlocks.APPLE_OAK_LEAVES
        );
        event.blockColors.registerColorProvider(
                (blockState, blockView, blockPos, index) -> Block.LEAVES.getColor(0),
                BroomBlocks.OAK_LEAVES
        );
        event.blockColors.registerColorProvider(
                (blockState, blockView, blockPos, index) -> Block.LEAVES.getColor(1),
                BroomBlocks.SPRUCE_LEAVES
        );
        event.blockColors.registerColorProvider(
                (blockState, blockView, blockPos, index) -> Block.LEAVES.getColor(2),
                BroomBlocks.BIRCH_LEAVES
        );
    }

    @EventListener
    public void registerItemColors(ItemColorsRegisterEvent event) {
        event.itemColors.register(
                (item, damage) -> class_287.method_981(1.0F, 1.0F),
                Block.GRASS_BLOCK,
                Block.GRASS,
                BroomBlocks.GRASS,
                BroomBlocks.FERN
        );
        event.itemColors.register(
                (item, damage) -> Block.LEAVES.getColor(0),
                BroomBlocks.APPLE_OAK_LEAVES
        );
    }
}
