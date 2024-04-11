package me.huedev.broom.mixin.common.entity;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author DanyGames2014
 */
@Mixin(SheepEntity.class)
public class SheepEntityMixin extends AnimalEntity {
    public SheepEntityMixin(World arg) {
        super(arg);
    }

    @Inject(method = "method_1323", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/Entity;)V"))
    public void broom_soundOnShear(PlayerEntity player, CallbackInfoReturnable<Boolean> cir){
        player.world.playSound(player, "broom:entity.sheep.shear", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }
}
