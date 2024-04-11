package me.huedev.broom.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DanyGames2014
 */
@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {
    @Shadow
    protected abstract void drawForeground();

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawForeground()V"
            )
    )
    public void broom_cancelDrawForeground(HandledScreen instance) {
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.GETFIELD,
                    target = "Lnet/minecraft/entity/player/ClientPlayerEntity;inventory:Lnet/minecraft/entity/player/PlayerInventory;"
            )
    )
    public void broom_injectDrawForeground(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        drawForeground();
    }

    @Inject(
            method = "drawSlot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/texture/TextureManager;bindTexture(I)V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void broom_addArmorSlotIcon(Slot slot, CallbackInfo ci) {
        if (slot.method_471() == 7355608) {
            this.minecraft.textureManager.bindTexture(this.minecraft.textureManager.getTextureId("/assets/broom/textures/gui/armor_icons.png"));
            this.drawTexture(slot.x, slot.y, 0, (slot.id - 5) * 16, 16, 16);
            GL11.glEnable(2896);
            ci.cancel();
        }
    }
}
