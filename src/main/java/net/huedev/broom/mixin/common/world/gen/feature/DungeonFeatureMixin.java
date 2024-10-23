package net.huedev.broom.mixin.common.world.gen.feature;

import net.huedev.broom.block.BroomBlocks;
import net.huedev.broom.item.BroomItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.world.gen.feature.DungeonFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

/**
 * @author Jadestrouble
 */
@Mixin(DungeonFeature.class)
public class DungeonFeatureMixin {
    @Inject(method = "getRandomChestItem", at = @At("RETURN"), cancellable = true)
    private void broom_replaceDungeonLoot(Random random, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = cir.getReturnValue();
        if (stack == null) {
            return;
        }
        if (cir.getReturnValue().getItem() instanceof MusicDiscItem) {
            cir.setReturnValue(new ItemStack(BroomItems.MUSIC_DISCS[random.nextInt(BroomItems.MUSIC_DISCS.length)], 1));
        }
        if (cir.getReturnValue().getItem() == Item.DYE) {
            cir.setReturnValue(new ItemStack(BroomBlocks.CACAO_SAPLING, 1));
        }
    }
}
