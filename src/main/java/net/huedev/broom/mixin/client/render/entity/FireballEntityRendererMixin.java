package net.huedev.broom.mixin.client.render.entity;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FireballEntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FireballEntityRenderer.class)
public class FireballEntityRendererMixin extends EntityRenderer {
    @Override
    public void render(Entity arg, double d, double e, double f, float g, float h) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) e, (float) f);
        GL11.glEnable(32826);
        float var10 = 2.0F;
        GL11.glScalef(var10, var10, var10);
        this.bindTexture("/assets/broom/textures/misc/fireball.png");
        Tessellator var12 = Tessellator.INSTANCE;
        float var13 = 0.0F;
        float var14 = 1.0F;
        float var15 = 0.0F;
        float var16 = 1.0F;
        float var17 = 1.0F;
        float var18 = 0.5F;
        float var19 = 0.25F;
        GL11.glRotatef(180.0F - this.dispatcher.yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.dispatcher.pitch, 1.0F, 0.0F, 0.0F);
        var12.startQuads();
        var12.normal(0.0F, 1.0F, 0.0F);
        var12.vertex((double)(0.0F - var18), (double)(0.0F - var19), 0.0, (double)var13, (double)var16);
        var12.vertex((double)(var17 - var18), (double)(0.0F - var19), 0.0, (double)var14, (double)var16);
        var12.vertex((double)(var17 - var18), (double)(1.0F - var19), 0.0, (double)var14, (double)var15);
        var12.vertex((double)(0.0F - var18), (double)(1.0F - var19), 0.0, (double)var13, (double)var15);
        var12.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}
