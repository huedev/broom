package net.huedev.broom.world.gen.feature;

import net.huedev.broom.block.BroomBlocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.block.BlockState;

import java.util.Random;

public class BroomGrassPatchFeature extends Feature {
    private static final BlockState STATE = BroomBlocks.TALL_GRASS.getDefaultState();

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        BlockState worldState = world.getBlockState(x, y, z);
        if (!worldState.isAir()) return false;
        if (STATE.getBlock().canPlaceAt(world, x, y, z)) {
            world.setBlockStateWithNotify(x, y, z, STATE);
            world.setBlockMeta(x, y, z, 0);
            return true;
        }
        return false;
    }
}
