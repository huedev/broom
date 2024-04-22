package me.huedev.broom.mixin.common.entity;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor
    float getField_1636();

    @Accessor("field_1636")
    void broom_setFallDistance(float fallDistance);
}
