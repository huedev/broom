package net.huedev.broom.mixin.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author DanyGames2014
 */
@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin extends Entity {
    public ArrowEntityMixin(World world) {
        super(world);
    }

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/Entity;I)Z"
            )
    )
    public boolean broom_setAttackedEntityOnFire(Entity attackedEntity, Entity damageSource, int amount) {
        if (this.fireTicks > 0) {
            attackedEntity.fireTicks = 100;
        }
        return attackedEntity.damage(damageSource, amount);
    }
}
