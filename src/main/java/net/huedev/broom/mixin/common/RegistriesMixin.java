package net.huedev.broom.mixin.common;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.modificationstation.stationapi.api.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author DanyGames2014
 */
@Mixin(Registries.class)
public class RegistriesMixin {
    @WrapWithCondition(method = "bootstrap", at = @At(value = "INVOKE", target = "Lnet/modificationstation/stationapi/api/registry/Registries;freezeRegistries()V"), remap = false, require = 0)
    private static boolean deleteFreezing() {
        return false;
    }
}
