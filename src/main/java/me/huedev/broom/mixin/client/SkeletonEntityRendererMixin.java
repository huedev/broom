package me.huedev.broom.mixin.client;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DanyGames2014
 */
@Mixin(SkeletonEntityRenderer.class)
public abstract class SkeletonEntityRendererMixin extends EntityRenderer {
    @Inject(
            method = "method_827",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/class_556;method_1862(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
                    shift = At.Shift.BEFORE
            )
    )
    public void broom_changeBowPosition(LivingEntity entity, float f, CallbackInfo ci) {
        ItemStack stack = entity.method_909();
        if (stack != null && stack.getItem() instanceof BowItem) {
            GL11.glRotatef(-5.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.2F, -0.5F, 0.2F);
        }
    }
}
