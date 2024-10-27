package net.huedev.broom.mixin.common.entity.passive;

import net.huedev.broom.block.BroomBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author DanyGames2014
 */
@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity {
    @Shadow
    public abstract int getColor();

    public SheepEntityMixin(World arg) {
        super(arg);
    }

    @Redirect(method = "dropItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;dropItem(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;"))
    public ItemEntity broom_dropFlatteningWool(SheepEntity instance, ItemStack stack, float yOffset) {
        return this.dropItem(new ItemStack(BroomBlocks.getWoolFromColor(this.getColor()), 1), 0.0F);
    }

    @Redirect(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;dropItem(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;"))
    public ItemEntity broom_shearFlatteningWool(SheepEntity instance, ItemStack stack, float yOffset) {
        return this.dropItem(new ItemStack(BroomBlocks.getWoolFromColor(this.getColor()), 1), 1.0F);
    }

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/Entity;)V"))
    public void broom_soundOnShear(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        player.world.playSound(player, "broom:entity.sheep.shear", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }
}
