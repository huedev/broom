package me.huedev.broom.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.MultiplayerInteractionManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author paulevsGitch
 */
@Mixin(MultiplayerInteractionManager.class)
public class MultiplayerInteractionManagerMixin {
    @WrapOperation(method = "method_1707", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/Block;onBlockBreakStart(Lnet/minecraft/world/World;IIILnet/minecraft/entity/player/PlayerEntity;)V"
    ))
    private void broom_disableActivation(Block instance, World world, int x, int y, int z, PlayerEntity player, Operation<Void> original) {}
}
