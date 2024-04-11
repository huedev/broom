package me.huedev.broom.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawContext {
    @Shadow
    private Minecraft minecraft;

    // X Position
    @ModifyConstant(method = "render", constant = @Constant(stringValue = "x: "))
    private String broom_renameXPosition(String value) {
        return "X: ";
    }

    // Y Position
    @ModifyConstant(method = "render", constant = @Constant(stringValue = "y: "))
    private String broom_renameYPosition(String value) {
        return "Y: ";
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                    ordinal = 3
            ),
            index = 3
    )
    private int broom_alignYPosition(int value) {
        return 74;
    }

    // Z Position
    @ModifyConstant(method = "render", constant = @Constant(stringValue = "z: "))
    private String broom_renameZPosition(String value) {
        return "Z: ";
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                    ordinal = 4
            ),
            index = 3
    )
    private int broom_alignZPosition(int value) {
        return 84;
    }

    // Facing
    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                    ordinal = 5
            ),
            index = 1
    )
    private String broom_facingDirection(String value) {
        Direction facing = Direction.fromRotation(this.minecraft.player.yaw);
        return "Facing: " + facing.toString();
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                    ordinal = 5
            ),
            index = 3
    )
    private int broom_alignFacing(int value) {
        return 94;
    }

    // Seed
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                    ordinal = 5
            )
    )
    private void broom_additionalDebugInfo(CallbackInfo ci, @Local TextRenderer var8) {
        this.drawTextWithShadow(var8, "Seed: " + minecraft.world.getSeed(), 2, 104, 14737632);
    }
}
