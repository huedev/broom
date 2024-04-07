package me.huedev.broom.mixin.common;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WoolBlockItem;
import net.modificationstation.stationapi.api.vanillafix.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WoolBlockItem.class)
public class WoolBlockItemMixin extends BlockItem {
    public WoolBlockItemMixin(int i) {
        super(i);
    }

    @Inject(method = "getTranslationKey", at = @At("HEAD"), cancellable = true)
    @Environment(EnvType.CLIENT)
    public void getTranslationKey(ItemStack stack, CallbackInfoReturnable<String> cir) {
        String[] originalKeyParts = super.getTranslationKey().split("[.]");
        cir.setReturnValue(originalKeyParts[0] + "." + DyeColor.byId(stack.getDamage()) + "_" + originalKeyParts[1]);
    }
}
