package net.huedev.broom.mixin.common.entity;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor
    float getFallDistance();

    @Accessor("fallDistance")
    void broom_setFallDistance(float fallDistance);
}
