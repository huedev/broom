package net.huedev.broom.mixin.client.render;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DanyGames2014
 */
@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin extends LivingEntityRenderer {
    @Shadow
    private BipedEntityModel armor2;

    public PlayerEntityRendererMixin(EntityModel arg, float f) {
        super(arg, f);
    }

    @Inject(
            method = "renderMore(Lnet/minecraft/entity/player/PlayerEntity;F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
                    shift = At.Shift.BEFORE
            )
    )
    public void broom_changeBowPosition(PlayerEntity player, float f, CallbackInfo ci) {
        ItemStack stack = player.getHand();
        if (stack != null && stack.getItem() instanceof BowItem) {
            GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        }
    }

    @Inject(method = "render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V", at = @At("HEAD"))
    public void broom_fixLeggings(PlayerEntity player, double e, double f, double g, float h, float par6, CallbackInfo ci) {
        ItemStack stack = player.inventory.armor[1];
        if (stack != null) {
            if (stack.getItem() instanceof ArmorItem) {
                this.armor2.riding = this.model.riding;
            }
        }
    }
}
