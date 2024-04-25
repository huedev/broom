package me.huedev.broom.mixin.common.item;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public interface ItemAccessor {
    @Accessor("handheld")
    void broom_setHandheld(boolean isHandheld);
}
