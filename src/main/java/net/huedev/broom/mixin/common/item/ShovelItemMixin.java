package net.huedev.broom.mixin.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ShovelItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
    @Shadow
    private static Block[] shovelEffectiveBlocks;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void broom_addEffectiveBlocks(CallbackInfo ci) {
        List<Block> blocks = new ArrayList<>(Arrays.asList(shovelEffectiveBlocks));
        blocks.add(Block.SOUL_SAND);
        shovelEffectiveBlocks = blocks.toArray(Block[]::new);
    }
}
