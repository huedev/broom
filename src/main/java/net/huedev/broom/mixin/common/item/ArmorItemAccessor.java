package net.huedev.broom.mixin.common.item;

import net.minecraft.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArmorItem.class)
public interface ArmorItemAccessor {
    @Mutable
    @Accessor("maxProtection")
    void broom_setMaxProtection(int value);
}
