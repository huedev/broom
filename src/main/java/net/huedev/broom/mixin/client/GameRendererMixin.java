package net.huedev.broom.mixin.client;

import net.huedev.broom.util.BroomOptions;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DanyGames2014
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    private Minecraft client;

    @Shadow
    private float viewDistance;

    @Unique
    public float broom_getFovMultiplier(float f, boolean isHand) {
        LivingEntity entity = this.client.camera;
        float fov = BroomOptions.getFovInDegrees();

        if (isHand) {
            fov = 70F;
        }

        if (entity.isInFluid(Material.WATER)) {
            fov *= 60.0F / 70.0F;
        }

        if (entity.health <= 0) {
            float deathTimeFov = (float) entity.deathTime + f;
            fov /= (1.0F - 500F / (deathTimeFov + 500F)) * 2.0F + 1.0F;
        }

        return fov;
    }

    @Unique
    public float broom_getFovMultiplier(float f) {
        return broom_getFovMultiplier(f, false);
    }

    @Redirect(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;getFov(F)F"))
    public float broom_redirectToCustomFov(GameRenderer instance, float value) {
        return broom_getFovMultiplier(value);
    }

    @Inject(method = "renderFirstPersonHand", at = @At(value = "HEAD"))
    public void broom_adjustHandFov(float f, int i, CallbackInfo ci) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(broom_getFovMultiplier(f, true), (float) client.displayWidth / (float) client.displayHeight, 0.05F, viewDistance * 2.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    @ModifyConstant(method = "onFrameUpdate", constant = @Constant(intValue = 200))
    public int broom_modifyPerformanceTargetFps(int constant){
        return BroomOptions.getFpsLimitValue();
    }

    @ModifyConstant(method = "onFrameUpdate", constant = @Constant(intValue = 120))
    public int broom_modifyBalancedTargetFps(int constant){
        return BroomOptions.getFpsLimitValue();
    }

    @ModifyConstant(method = "onFrameUpdate", constant = @Constant(intValue = 40))
    public int broom_modifyPowerSaverTargetFps(int constant){
        return BroomOptions.getFpsLimitValue();
    }

    @Redirect(method = "onFrameUpdate", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/option/GameOptions;fpsLimit:I"))
    public int broom_overridePerformanceLevel(GameOptions instance){
        return BroomOptions.getPerformanceLevel();
    }
}
