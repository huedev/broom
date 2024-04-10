package me.huedev.broom.mixin.common.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SlabBlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlabBlockItem.class)
public class SlabBlockItemMixin extends BlockItem {
    @Unique
    private static final String[] names = new String[] {"stone", "sandstone", "wooden", "cobblestone"};

    public SlabBlockItemMixin(int i) {
        super(i);
    }

    @Inject(method = "getTranslationKey", at = @At("HEAD"), cancellable = true)
    @Environment(EnvType.CLIENT)
    public void getTranslationKey(ItemStack stack, CallbackInfoReturnable<String> cir) {
        if (stack.getDamage() > names.length) {
            cir.setReturnValue(null);
        } else {
            String[] originalKeyParts = super.getTranslationKey().split("[.]");
            cir.setReturnValue(originalKeyParts[0] + "." + names[stack.getDamage()] + "_" + originalKeyParts[1]);
        }
    }
}
