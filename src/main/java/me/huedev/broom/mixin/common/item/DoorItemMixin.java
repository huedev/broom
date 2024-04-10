package me.huedev.broom.mixin.common.item;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DoorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoorItem.class)
public class DoorItemMixin {
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;method_201(IIIII)Z", ordinal = 0))
    private void broom_playBlockPlaceSound(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side, CallbackInfoReturnable<Boolean> cir, @Local Block var8) {
        world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), var8.soundGroup.getBreakSound(), (var8.soundGroup.method_1976() + 1.0F) / 2.0F, var8.soundGroup.method_1977() * 0.8F);
    }
}
