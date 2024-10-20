package net.huedev.broom.mixin.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author telvarost
 */
@Mixin(Minecraft.class)
public interface MinecraftAccessor {
    @Accessor(value = "INSTANCE")
    static Minecraft broom_getInstance() {
        throw new UnsupportedOperationException();
    }
}
