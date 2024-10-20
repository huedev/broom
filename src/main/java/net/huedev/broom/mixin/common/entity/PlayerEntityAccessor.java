package net.huedev.broom.mixin.common.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerEntity.class)
public interface PlayerEntityAccessor {
    @Accessor
    Vec3i getField_516();

    @Accessor("field_516")
    void broom_setRespawnPosition(Vec3i respawnPosition);
}
