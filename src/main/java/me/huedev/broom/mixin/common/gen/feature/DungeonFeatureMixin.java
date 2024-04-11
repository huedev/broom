package me.huedev.broom.mixin.common.gen.feature;

import me.huedev.broom.item.BroomItems;
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
    private void broom_replaceMusicDisc(Random random, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = cir.getReturnValue();
        if (stack == null) {
            return;
        }
        if (cir.getReturnValue().getItem() instanceof MusicDiscItem) {
            cir.setReturnValue(new ItemStack(BroomItems.MUSIC_DISCS[random.nextInt(BroomItems.MUSIC_DISCS.length)], 1));
        }
    }
}
