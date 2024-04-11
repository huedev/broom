package me.huedev.broom.mixin.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author DanyGames2014
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {
    public PlayerEntityMixin(World world) {
        super(world);
    }

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
}
