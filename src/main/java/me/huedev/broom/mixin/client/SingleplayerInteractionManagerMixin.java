package me.huedev.broom.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.SingleplayerInteractionManager;
import net.minecraft.block.Block;
import net.minecraft.client.InteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author paulevsGitch
 */
@Mixin(SingleplayerInteractionManager.class)
public class SingleplayerInteractionManagerMixin extends InteractionManager {
    public SingleplayerInteractionManagerMixin(Minecraft minecraft) {
        super(minecraft);
    }

    @WrapOperation(method = "method_1707", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/Block;onBlockBreakStart(Lnet/minecraft/world/World;IIILnet/minecraft/entity/player/PlayerEntity;)V"
    ))
    private void broom_disableActivation(Block block, World world, int x, int y, int z, PlayerEntity player, Operation<Void> original) {}
}
