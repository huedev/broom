package me.huedev.broom.mixin.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignModel;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(SignBlockEntityRenderer.class)
public class SignBlockEntityRendererMixin extends BlockEntityRenderer {
    @Shadow private SignModel model;

    @Override
    public void render(BlockEntity blockEntity, double d, double e, double f, float g) {
        SignBlockEntity arg = (SignBlockEntity) blockEntity;
        Block var9 = arg.getBlock();
        GL11.glPushMatrix();
        float var10 = 1.0F;
        float var12;
        if (var9 == Block.SIGN) {
            GL11.glTranslatef((float)d + 0.5F, (float)e + 0.75F * var10, (float)f + 0.5F);
            float var11 = (float)(arg.getPushedBlockData() * 360) / 16.0F;
            GL11.glRotatef(-var11, 0.0F, 1.0F, 0.0F);
            this.model.stick.visible = true;
        } else {
            int var16 = arg.getPushedBlockData();
            var12 = 0.0F;
            if (var16 == 2) {
                var12 = 180.0F;
            }

            if (var16 == 4) {
                var12 = 90.0F;
            }

            if (var16 == 5) {
                var12 = -90.0F;
            }

            GL11.glTranslatef((float)d + 0.5F, (float)e + 0.75F * var10, (float)f + 0.5F);
            GL11.glRotatef(-var12, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
            this.model.stick.visible = false;
        }

        this.bindTexture("/assets/broom/textures/item/sign.png");
        GL11.glPushMatrix();
        GL11.glScalef(var10, -var10, -var10);
        this.model.render();
        GL11.glPopMatrix();
        TextRenderer var17 = this.getTextRenderer();
        var10 = 0.6666667F;
        var12 = 0.016666668F * var10;
        GL11.glTranslatef(0.0F, 0.1F * var10, 0.1F * var10);
        GL11.glScalef(var12, -var12, var12);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F * var12);
        GL11.glDepthMask(false);
        byte var13 = 0;

        for (int var14 = 0; var14 < arg.texts.length; ++var14) {
            String var15 = arg.texts[var14];
            if (var14 == arg.currentRow) {
                var15 = "> " + var15 + " <";
                var17.draw(var15, -var17.getWidth(var15) / 2, var14 * 10 - arg.texts.length * 5, var13);
            } else {
                var17.draw(var15, -var17.getWidth(var15) / 2, var14 * 10 - arg.texts.length * 5, var13);
            }
        }

        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
}
