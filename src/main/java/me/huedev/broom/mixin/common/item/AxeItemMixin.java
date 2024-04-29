package me.huedev.broom.mixin.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author paulevsGitch
 */
@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Shadow
    private static Block[] axeEffectiveBlocks;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void broom_addEffectiveBlocks(CallbackInfo ci) {
        List<Block> blocks = new ArrayList<>(Arrays.asList(axeEffectiveBlocks));
        blocks.add(Block.CRAFTING_TABLE);
        blocks.add(Block.WOODEN_STAIRS);
        blocks.add(Block.JUKEBOX);
        blocks.add(Block.SIGN);
        blocks.add(Block.WALL_SIGN);
        blocks.add(Block.LADDER);
        blocks.add(Block.DOOR);
        blocks.add(Block.TRAPDOOR);
        blocks.add(Block.PUMPKIN);
        blocks.add(Block.JACK_O_LANTERN);
        blocks.add(Block.FENCE);
        blocks.add(Block.NOTE_BLOCK);
        blocks.add(Block.BED);
        blocks.add(Block.WOODEN_PRESSURE_PLATE);
        axeEffectiveBlocks = blocks.toArray(Block[]::new);
    }
}
