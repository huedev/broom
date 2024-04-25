package me.huedev.broom.mixin.common.block;

import net.minecraft.block.DoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoorBlock.class)
public class DoorBlockMixin {
    @Inject(method = "onBlockBreakStart", at = @At("HEAD"), cancellable = true)
    private void broom_cancelInteract(World world, int x, int y, int z, PlayerEntity player, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "onUse",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;method_173(Lnet/minecraft/entity/player/PlayerEntity;IIIII)V"
            ),
            cancellable = true
    )
    private void broom_onUseOpenCloseSound(World world, int x, int y, int z, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        int meta = world.getBlockMeta(x, y, z);
        boolean opened = (meta & 4) != 0;
        if (!world.isRemote) {
            if (opened) {
                world.method_173(null, 1006, x, y, z, 0);
            } else {
                world.method_173(null, 1007, x, y, z, 0);
            }
        }
        cir.setReturnValue(true);
    }

    @Inject(
            method = "method_837",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;method_173(Lnet/minecraft/entity/player/PlayerEntity;IIIII)V"
            ),
            cancellable = true
    )
    private void broom_onRedstoneOpenCloseSound(World world, int x, int y, int z, boolean bl, CallbackInfo ci) {
        int meta = world.getBlockMeta(x, y, z);
        boolean opened = (meta & 4) != 0;
        if (!world.isRemote) {
            if (opened) {
                world.method_173(null, 1006, x, y, z, 0);
            } else {
                world.method_173(null, 1007, x, y, z, 0);
            }
        }
        ci.cancel();
    }
}
