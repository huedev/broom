package net.huedev.broom.mixin.common.world.gen.chunk;

import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {
    @ModifyConstant(method = "decorate", constant = @Constant(intValue = 5, ordinal = 1))
    private int broom_increaseRainforestTreeDensity(int density) {
        return 10;
    }
}
