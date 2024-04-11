package me.huedev.broom.mixin.client.entity;

import net.minecraft.entity.player.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @ModifyConstant(method = "<init>", constant = @Constant(stringValue = "http://s3.amazonaws.com/MinecraftSkins/"))
    public String broom_useBetacraftSkinURL(String value) {
        return "https://betacraft.uk/MinecraftSkins/";
    }
}
