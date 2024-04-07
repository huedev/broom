package me.huedev.broom.listener;

import me.huedev.broom.block.BroomBlocks;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.client.event.color.block.BlockColorsRegisterEvent;

public class BlockColorListener {
    @EventListener
    private void registerBlockColors(BlockColorsRegisterEvent event) {
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
}
