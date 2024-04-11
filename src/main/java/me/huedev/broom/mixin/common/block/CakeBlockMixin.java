package me.huedev.broom.mixin.common.block;

import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(CakeBlock.class)
public class CakeBlockMixin {
    @Unique
    private static final Random random = new Random();

    @Inject(method = "method_1528", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;method_939(I)V"))
    public void broom_playSoundOnEat(World world, int x, int y, int z, PlayerEntity player, CallbackInfo ci) {
        world.playSound(player, "random.eat", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }
}
