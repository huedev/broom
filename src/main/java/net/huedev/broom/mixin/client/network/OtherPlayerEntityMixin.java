package net.huedev.broom.mixin.client.network;

import net.minecraft.client.network.OtherPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(OtherPlayerEntity.class)
public class OtherPlayerEntityMixin {
    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "http://s3.amazonaws.com/MinecraftSkins/"))
    public String broom_useBetacraftSkinURL(String value) {
        return "https://betacraft.uk/MinecraftSkins/";
    }
}
