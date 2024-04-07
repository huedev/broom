package me.huedev.broom.block;

import me.huedev.broom.Broom;
import net.minecraft.block.Block;

public class BroomBlocks {
    public static Block OAK_LOG;
    public static Block SPRUCE_LOG;
    public static Block BIRCH_LOG;
    public static Block OAK_LEAVES;
    public static Block SPRUCE_LEAVES;
    public static Block BIRCH_LEAVES;

    public static void init() {
        OAK_LOG = new BroomLogBlock(Broom.id("oak_log"));
        SPRUCE_LOG = new BroomLogBlock(Broom.id("spruce_log"));
        BIRCH_LOG = new BroomLogBlock(Broom.id("birch_log"));
        OAK_LEAVES = new VanillaLeavesWrapper(Broom.id("oak_leaves"), 0);
        SPRUCE_LEAVES = new VanillaLeavesWrapper(Broom.id("spruce_leaves"), 1);
        BIRCH_LEAVES = new VanillaLeavesWrapper(Broom.id("birch_leaves"), 2);
    }
}
