package me.huedev.broom.mixin.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.block.entity.SignModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(SignModel.class)
public class SignModelMixin {
    @Shadow public ModelPart root;

    @Shadow public ModelPart stick;

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;addCuboid(FFFIIIF)V", ordinal = 0))
    private void broom_changeSignRootModel(ModelPart instance, float y, float z, float sizeX, int sizeY, int sizeZ, int dilation, float v) {
        this.root.addCuboid(-8.0F, -5.0F, -1.0F, 16, 8, 2, 0.0F);
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;<init>(II)V", ordinal = 1), index = 1)
    private int broom_changeSignStickModelUVs(int v) {
        return 15;
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;addCuboid(FFFIIIF)V", ordinal = 1))
    private void broom_changeSignStickModel(ModelPart instance, float y, float z, float sizeX, int sizeY, int sizeZ, int dilation, float v) {
        this.stick.addCuboid(-1.0F, 3.0F, -1.0F, 2, 9, 2, 0.0F);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void broom_render(CallbackInfo ci) {
        this.root.render(0.0625F);
        this.stick.render(0.0625F);
        ci.cancel();
    }
}
