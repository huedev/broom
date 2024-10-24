package net.huedev.broom.mixin.common.world.biome;

import net.huedev.broom.world.gen.feature.CacaoTreeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.RainforestBiome;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(RainforestBiome.class)
public class RainforestBiomeMixin extends Biome {
    @Inject(method = "getRandomTreeFeature", at = @At("HEAD"), cancellable = true)
    public void broom_addCacaoTrees(Random random, CallbackInfoReturnable<Feature> cir) {
        cir.setReturnValue(new CacaoTreeFeature());
    }
}
