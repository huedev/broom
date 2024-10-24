package net.huedev.broom.mixin.common.entity.passive;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public class CowEntityMixin extends AnimalEntity {
    public CowEntityMixin(World arg) {
        super(arg);
    }

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;setStack(ILnet/minecraft/item/ItemStack;)V"))
    public void broom_soundOnMilk(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        player.world.playSound(player, "broom:entity.cow.milk", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }
}
