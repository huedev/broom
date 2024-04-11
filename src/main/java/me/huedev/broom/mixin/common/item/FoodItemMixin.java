package me.huedev.broom.mixin.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

/**
 * @author DanyGames2014
 */
@Mixin(FoodItem.class)
public class FoodItemMixin {
    @Unique
    private static final Random random = new Random();

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    public void broom_preventEatingWithFullHealth(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        if (player.health >= 20) {
            cir.setReturnValue(stack);
        }
    }

    @Inject(method = "use", at = @At(value = "HEAD"))
    public void broom_playSoundOnEat(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        if (player.health < 20) {
            world.playSound(player, "random.eat", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }
    }
}
