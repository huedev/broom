package me.huedev.broom.block;

import me.huedev.broom.Broom;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

/**
 * @author paulevsGitch
 */
public class BroomBlockTags {
    public static final TagKey<Block> REQUIRES_POWER = get("requires_power");
    public static final TagKey<Block> LEAVES = getDefault("leaves");
    public static final TagKey<Block> LOGS = getDefault("logs");
    public static final TagKey<Block> DIRT = get("dirt");
    public static final TagKey<Block> DEAD_BUSH_PLANTABLE_ON = get("dead_bush_plantable_on");
    public static final TagKey<Block> DRIES_OUT_SAPLING = get("dries_out_sapling");
    public static final TagKey<Block> SAPLING_PLANTABLE_ON = get("sapling_plantable_on");
    public static final TagKey<Block> SUGAR_CANE_PLANTABLE_ON = get("sugar_cane_plantable_on");
    public static final TagKey<Block> MAINTAINS_FARMLAND = get("maintains_farmland");

    private static TagKey<Block> get(String name) {
        return TagKey.of(BlockRegistry.KEY, Broom.id(name));
    }

    private static TagKey<Block> getDefault(String name) {
        return TagKey.of(BlockRegistry.KEY, Identifier.of(name));
    }
}
