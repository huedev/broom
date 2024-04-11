package me.huedev.broom.mixin.common.entity;

import me.huedev.broom.mixin.client.MinecraftAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {
    public PlayerEntityMixin(World world) {
        super(world);
    }

    /**
     * @author DanyGames2014
     */
    @Inject(method = "damage(Lnet/minecraft/entity/Entity;I)Z", at = @At("HEAD"))
    public void broom_setOnFireIfAttackerBurning(Entity attackerEntity, int amount, CallbackInfoReturnable<Boolean> cir) {
        if (!this.world.isRemote) {
            if (!(attackerEntity instanceof SkeletonEntity) && attackerEntity != null) {
                if (this.random.nextInt(0, 100) < 50) {
                    if (attackerEntity.fire > 0) {
                        this.fire = 100;
                    }
                }
            }
        }
    }

    /**
     * @author telvarost
     */
    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    private void broom_dropSelectedItem(CallbackInfo ci) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            Minecraft minecraft = MinecraftAccessor.broom_getInstance();
            PlayerEntity player = (PlayerEntity) (Object) this;

            minecraft.interactionManager.clickSlot(0, 36 + player.inventory.selectedSlot, 0, false, minecraft.player);
            minecraft.interactionManager.clickSlot(0, -999, 0, false, minecraft.player);
            ci.cancel();
        }
    }
}
