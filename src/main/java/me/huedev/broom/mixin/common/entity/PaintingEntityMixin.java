package me.huedev.broom.mixin.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PaintingEntity.class)
public abstract class PaintingEntityMixin extends Entity {
    public PaintingEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;method_210(Lnet/minecraft/entity/Entity;)Z"))
    private void broom_playBlockBreakSound(Entity amount, int par2, CallbackInfoReturnable<Boolean> cir) {
        world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), Block.WOOD_SOUND_GROUP.getSound(), (Block.WOOD_SOUND_GROUP.method_1976() + 1.0F) / 2.0F, Block.WOOD_SOUND_GROUP.method_1977() * 0.8F);
    }
}
