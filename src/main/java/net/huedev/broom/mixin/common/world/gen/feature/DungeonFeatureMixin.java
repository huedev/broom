package net.huedev.broom.mixin.common.world.gen.feature;

import com.llamalad7.mixinextras.sugar.Local;
import net.huedev.broom.block.BroomBlocks;
import net.huedev.broom.item.BroomItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.world.gen.feature.DungeonFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

/**
 * @author Jadestrouble
 */
@Mixin(DungeonFeature.class)
public class DungeonFeatureMixin {
    @ModifyConstant(method = "getRandomChestItem", constant = @Constant(intValue = 11))
    private int broom_increaseDungeonLootOptions(int constant) {
        return 12;
    }

    @Inject(method = "getRandomChestItem", at = @At("RETURN"), cancellable = true)
    private void broom_replaceDungeonLoot(Random random, CallbackInfoReturnable<ItemStack> cir, @Local int var2) {
        ItemStack stack = cir.getReturnValue();
        if (stack == null) {
            if (var2 == 11) {
                cir.setReturnValue(new ItemStack(Block.SPONGE, random.nextInt(4) + 1));
            }
            return;
        }
        if (stack.getItem() instanceof MusicDiscItem) {
            cir.setReturnValue(new ItemStack(BroomItems.MUSIC_DISCS[random.nextInt(BroomItems.MUSIC_DISCS.length)], 1));
        }
        if (stack.getItem() == Item.DYE) {
            cir.setReturnValue(new ItemStack(BroomBlocks.CACAO_SAPLING, random.nextInt(4) + 1));
        }
    }
}
