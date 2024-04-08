package me.huedev.broom.util;

import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.impl.world.chunk.ChunkSection;
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk;

public class WorldHelper {
    /**
     * @author paulevsGitch
     */
    public static void setBlockSilent(World world, int x, int y, int z, BlockState state) {
        FlattenedChunk chunk = (FlattenedChunk) world.method_214(x >> 4, z >> 4);
        int index = world.getSectionIndex(y);
        ChunkSection section = chunk.sections[index];
        if (section == null) {
            section = new ChunkSection(index);
            chunk.sections[index] = section;
        }
        section.setBlockState(x & 15, y & 15, z & 15, state);
    }
}
