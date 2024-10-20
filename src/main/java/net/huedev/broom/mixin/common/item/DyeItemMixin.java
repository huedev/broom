package net.huedev.broom.mixin.common.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DyeItem.class)
public class DyeItemMixin extends Item {
    @Unique
    private static final String[] names = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "light_gray", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"};

    public DyeItemMixin(int id) {
        super(id);
    }

    @Inject(method = "getTranslationKey", at = @At("HEAD"), cancellable = true)
    @Environment(EnvType.CLIENT)
    public void getTranslationKey(ItemStack stack, CallbackInfoReturnable<String> cir) {
        String[] originalKeyParts = super.getTranslationKey().split("[.]");
        cir.setReturnValue(originalKeyParts[0] + "." + names[stack.getDamage()] + "_" + originalKeyParts[1]);
    }
}
