package me.huedev.broom.mixin.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.PickaxeItem;
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
@Mixin(PickaxeItem.class)
public class PickaxeItemMixin {
    @Shadow
    private static Block[] pickaxeEffectiveBlocks;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void broom_addEffectiveBlocks(CallbackInfo ci) {
        List<Block> blocks = new ArrayList<>(Arrays.asList(pickaxeEffectiveBlocks));
        blocks.add(Block.GLASS);
        blocks.add(Block.DISPENSER);
        blocks.add(Block.BRICKS);
        blocks.add(Block.POWERED_RAIL);
        blocks.add(Block.DETECTOR_RAIL);
        blocks.add(Block.PISTON);
        blocks.add(Block.PISTON_HEAD);
        blocks.add(Block.STICKY_PISTON);
        blocks.add(Block.RAIL);
        blocks.add(Block.FURNACE);
        blocks.add(Block.LIT_FURNACE);
        blocks.add(Block.COBBLESTONE_STAIRS);
        blocks.add(Block.IRON_DOOR);
        blocks.add(Block.REDSTONE_ORE);
        blocks.add(Block.LIT_REDSTONE_ORE);
        blocks.add(Block.BUTTON);
        blocks.add(Block.SPAWNER);
        blocks.add(Block.GLOWSTONE);
        pickaxeEffectiveBlocks = blocks.toArray(Block[]::new);
    }
}
