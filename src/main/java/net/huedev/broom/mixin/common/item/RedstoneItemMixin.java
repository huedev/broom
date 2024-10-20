package net.huedev.broom.mixin.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RedstoneItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneItem.class)
public class RedstoneItemMixin {
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlock(IIII)Z"))
    private void broom_playBlockPlaceSound(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side, CallbackInfoReturnable<Boolean> cir) {
        world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), Block.REDSTONE_WIRE.soundGroup.getSound(), (Block.REDSTONE_WIRE.soundGroup.getVolume() + 1.0F) / 2.0F, Block.REDSTONE_WIRE.soundGroup.getPitch() * 0.8F);
    }
}
