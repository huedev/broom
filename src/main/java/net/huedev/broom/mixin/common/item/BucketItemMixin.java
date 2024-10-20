package net.huedev.broom.mixin.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BucketItem.class)
public class BucketItemMixin {
    @Unique
    private static final Random random = new Random();

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/item/Item;)V", ordinal = 2))
    public void broom_playSoundOnDrink(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        world.playSound(player, "random.drink", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }
}
