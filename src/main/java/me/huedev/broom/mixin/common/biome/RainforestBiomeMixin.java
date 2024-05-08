package me.huedev.broom.mixin.common.biome;

import me.huedev.broom.gen.feature.CacaoTreeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.RainforestBiome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LargeOakTreeFeature;
import net.minecraft.world.gen.feature.OakTreeFeature;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(RainforestBiome.class)
public class RainforestBiomeMixin extends Biome {
    @Override
    public Feature getRandomTreeFeature(Random random) {
        if (random.nextInt(5) == 0) {
            return new CacaoTreeFeature();
        } else {
            return (Feature)(random.nextInt(3) == 0 ? new LargeOakTreeFeature() : new OakTreeFeature());
        }
    }
}
