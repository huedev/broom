package net.huedev.broom.mixin.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignItem.class)
public class SignItemMixin {
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;method_201(IIIII)Z"))
    private void broom_playBlockPlaceSound(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side, CallbackInfoReturnable<Boolean> cir) {
        Block signBlock = Block.SIGN;
        world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), signBlock.soundGroup.getSound(), (signBlock.soundGroup.method_1976() + 1.0F) / 2.0F, signBlock.soundGroup.method_1977() * 0.8F);
    }
}
