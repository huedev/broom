package me.huedev.broom.mixin.common.block;

import net.minecraft.block.DoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DoorBlock.class)
public class DoorBlockMixin {
    @Inject(method = "onBlockBreakStart", at = @At("HEAD"), cancellable = true)
    private void broom_cancelInteract(World world, int x, int y, int z, PlayerEntity player, CallbackInfo ci) {
        ci.cancel();
    }
}
