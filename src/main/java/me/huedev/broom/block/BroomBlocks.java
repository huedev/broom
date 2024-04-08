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
    public static Block OAK_SAPLING;
    public static Block SPRUCE_SAPLING;
    public static Block BIRCH_SAPLING;

    public static void init() {
        OAK_LOG = new BroomLogBlock(Broom.id("oak_log"));
        SPRUCE_LOG = new BroomLogBlock(Broom.id("spruce_log"));
        BIRCH_LOG = new BroomLogBlock(Broom.id("birch_log"));
        OAK_LEAVES = new VanillaLeavesWrapper(Broom.id("oak_leaves"), 0);
        SPRUCE_LEAVES = new VanillaLeavesWrapper(Broom.id("spruce_leaves"), 1);
        BIRCH_LEAVES = new VanillaLeavesWrapper(Broom.id("birch_leaves"), 2);
        OAK_SAPLING = new BroomSaplingBlock(Broom.id("oak_sapling"), Block.SAPLING.getTexture(0, 0));
        SPRUCE_SAPLING = new BroomSaplingBlock(Broom.id("spruce_sapling"), Block.SAPLING.getTexture(0, 1));
        BIRCH_SAPLING = new BroomSaplingBlock(Broom.id("birch_sapling"), Block.SAPLING.getTexture(0, 2));
    }

    /**
     * @author paulevsGitch
     */
    public static Block getLogByMeta(int meta) {
        return switch (meta & 3) {
            case 1 -> SPRUCE_LOG;
            case 2 -> BIRCH_LOG;
            default -> OAK_LOG;
        };
    }

    /**
     * @author paulevsGitch
     */
    public static Block getLeavesByMeta(int meta) {
        return switch (meta & 3) {
            case 1 -> SPRUCE_LEAVES;
            case 2 -> BIRCH_LEAVES;
            default -> OAK_LEAVES;
        };
    }

    public static Block getSaplingByMeta(int meta) {
        return switch (meta & 3) {
            case 1 -> SPRUCE_SAPLING;
            case 2 -> BIRCH_SAPLING;
            default -> OAK_SAPLING;
        };
    }
}
