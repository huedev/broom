package me.huedev.broom.mixin.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author calmilamsy
 */
@Mixin(Entity.class)
public class EntityMixin {
    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;x:D", opcode = Opcodes.PUTFIELD))
    private void broom_fixX(Entity entity, double value) {
        if (!entity.world.isRemote || entity instanceof PlayerEntity || !(entity instanceof LivingEntity)) {
            entity.x = value;
        }
    }

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;y:D", opcode = Opcodes.PUTFIELD))
    private void broom_fixY(Entity entity, double value) {
        if (!entity.world.isRemote || entity instanceof PlayerEntity || !(entity instanceof LivingEntity)) {
            entity.y = value;
        }
    }

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;z:D", opcode = Opcodes.PUTFIELD))
    private void broom_fixZ(Entity entity, double value) {
        if (!entity.world.isRemote || entity instanceof PlayerEntity || !(entity instanceof LivingEntity)) {
            entity.z = value;
        }
    }
}
