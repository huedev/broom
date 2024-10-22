package net.huedev.broom.mixin.common.entity;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.huedev.broom.block.BroomBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
abstract public class LivingEntityMixin extends Entity {
    public LivingEntityMixin(World world) {
        super(world);
    }

    @WrapWithCondition(method = "onLanding", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/Entity;I)Z"))
    private boolean broom_reduceFallDamage(LivingEntity instance, Entity damageSource, int amount) {
        int landedOnBlockId = this.world.getBlockId(MathHelper.floor(this.x), MathHelper.floor(this.y - 0.20000000298023224 - (double)this.standingEyeHeight), MathHelper.floor(this.z));
        if (landedOnBlockId == BroomBlocks.HAY_BALE.id) {
            int reducedDamage = (int) (amount * 0.2F);
            this.damage(null, reducedDamage);
            return false;
        }
        else
        {
            return true;
        }
    }
}
