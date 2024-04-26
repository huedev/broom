package me.huedev.broom.mixin.client.gui.hud;

import net.minecraft.class_564;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.Chunk;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.worldgen.BiomeAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawContext {
    @Shadow
    private Minecraft minecraft;

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                    ordinal = 2
            ),
            index = 1
    )
    private String broom_removeVanillaXPosition(String value) {
        return "";
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                    ordinal = 3
            ),
            index = 1
    )
    private String broom_removeVanillaYPosition(String value) {
        return "";
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                    ordinal = 4
            ),
            index = 1
    )
    private String broom_removeVanillaZPosition(String value) {
        return "";
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                    ordinal = 5
            ),
            index = 1
    )
    private String broom_removeVanillaFacing(String value) {
        return "";
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V",
                    ordinal = 5
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void broom_additionalDebugInfo(float bl, boolean i, int j, int par4, CallbackInfo ci, class_564 scaler, int var6, int var7, TextRenderer var8) {
        int offset = 52;

        double playerX = minecraft.player.x;
        double playerY = minecraft.player.y - minecraft.player.eyeHeight;
        double playerZ = minecraft.player.z;
        String text = "XYZ: " + String.format("%.4f", playerX) + " " + String.format("%.4f", playerY) + " " + String.format("%.4f", playerZ);
        this.drawTextWithShadow(var8, text, 2, offset += 10, 14737632);

        double playerYaw = minecraft.player.yaw;
        double playerPitch = minecraft.player.pitch;
        Direction facing = Direction.fromRotation(playerYaw);
        text = "Facing: " + facing + " (" + String.format("%.1f", Math.abs(playerYaw % 360.0F)) + " " + String.format("%.1f", playerPitch % 360.0F) + ")";
        this.drawTextWithShadow(var8, text, 2, offset += 10, 14737632);

        int blockX = (int) Math.floor(minecraft.player.x);
        int blockY = (int) Math.floor(minecraft.player.y - minecraft.player.eyeHeight);
        int blockZ = (int) Math.floor(minecraft.player.z);
        int blockRelativeX = blockX & 15;
        int blockRelativeY = blockY & 15;
        int blockRelativeZ = blockZ & 15;
        text = "Block: " + blockX + " " + blockY + " " + blockZ + " [" + blockRelativeX + " " + blockRelativeY + " " + blockRelativeZ + "]";
        this.drawTextWithShadow(var8, text, 2, offset += 10, 14737632);

        int chunkX = blockX >> 4;
        int chunkY = blockY >> 4;
        int chunkZ = blockZ >> 4;
        text = "Chunk: " + chunkX + " " + chunkY + " " + chunkZ;
        this.drawTextWithShadow(var8, text, 2, offset += 10, 14737632);

        text = "Biome: " + minecraft.world.method_1781().method_1787(blockX, blockZ).name;
        this.drawTextWithShadow(var8, text, 2, offset += 10, 14737632);

        Chunk chunk = minecraft.world.method_199(blockX, blockZ);
        int skyLight = chunk.getLight(LightType.SKY, blockRelativeX, blockY, blockRelativeZ);
        int blockLight = chunk.getLight(LightType.BLOCK, blockRelativeX, blockY, blockRelativeZ);
        int light = Math.max(skyLight, blockLight);
        text = "Light: " + light + " (" + skyLight + " sky, " + blockLight + " block)";
        this.drawTextWithShadow(var8, text, 2, offset += 10, 14737632);

        text = "Seed: " + minecraft.world.getSeed();
        this.drawTextWithShadow(var8, text, 2, offset += 10, 14737632);
    }
}
