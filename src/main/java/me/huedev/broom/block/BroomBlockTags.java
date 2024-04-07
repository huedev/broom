package me.huedev.broom.block;

import me.huedev.broom.Broom;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

/**
 * @author paulevs
 */
public class BroomBlockTags {
    public static final TagKey<Block> LEAVES = getDefault("leaves");
    public static final TagKey<Block> LOGS = getDefault("logs");

    private static TagKey<Block> get(String name) {
        return TagKey.of(BlockRegistry.KEY, Broom.id(name));
    }

    private static TagKey<Block> getDefault(String name) {
        return TagKey.of(BlockRegistry.KEY, Identifier.of(name));
    }
}
