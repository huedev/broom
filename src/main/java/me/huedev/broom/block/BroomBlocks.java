package me.huedev.broom.block;

import me.huedev.broom.Broom;
import me.huedev.broom.gen.feature.BroomGrassPatchFeature;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.modificationstation.stationapi.api.bonemeal.BonemealAPI;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.template.block.TemplateStairsBlock;
import net.modificationstation.stationapi.api.template.block.TemplateTrapdoorBlock;

public class BroomBlocks {
    public static Block OAK_LOG;
    public static Block SPRUCE_LOG;
    public static Block BIRCH_LOG;
    public static Block OAK_LEAVES;
    public static Block APPLE_OAK_LEAVES;
    public static Block SPRUCE_LEAVES;
    public static Block BIRCH_LEAVES;
    public static Block OAK_SAPLING;
    public static Block SPRUCE_SAPLING;
    public static Block BIRCH_SAPLING;
    public static Block GRASS;
    public static Block FERN;
    public static Block PUMPKIN;
    public static Block PUMPKIN_CROPS;
    public static Block STONE_SLAB;
    public static Block STONE_DOUBLE_SLAB;
    public static Block SANDSTONE_SLAB;
    public static Block SANDSTONE_DOUBLE_SLAB;
    public static Block WOODEN_SLAB;
    public static Block WOODEN_DOUBLE_SLAB;
    public static Block COBBLESTONE_SLAB;
    public static Block COBBLESTONE_DOUBLE_SLAB;
    public static Block BRICK_SLAB;
    public static Block BRICK_DOUBLE_SLAB;
    public static Block BRICK_STAIRS;
    public static Block STONE_BRICKS;
    public static Block STONE_BRICK_SLAB;
    public static Block STONE_BRICK_DOUBLE_SLAB;
    public static Block STONE_BRICK_STAIRS;
    public static Block WOODEN_BUTTON;
    public static Block IRON_TRAPDOOR;

    public static void init() {
        OAK_LOG = new BroomLogBlock(Broom.id("oak_log"));
        SPRUCE_LOG = new BroomLogBlock(Broom.id("spruce_log"));
        BIRCH_LOG = new BroomLogBlock(Broom.id("birch_log"));

        OAK_LEAVES = new VanillaLeavesWrapper(Broom.id("oak_leaves"), 0);
        APPLE_OAK_LEAVES = new BroomAppleOakLeavesBlock(Broom.id("apple_oak_leaves"));
        SPRUCE_LEAVES = new VanillaLeavesWrapper(Broom.id("spruce_leaves"), 1);
        BIRCH_LEAVES = new VanillaLeavesWrapper(Broom.id("birch_leaves"), 2);

        OAK_SAPLING = new BroomSaplingBlock(Broom.id("oak_sapling"), Block.SAPLING.getTexture(0, 0));
        SPRUCE_SAPLING = new BroomSaplingBlock(Broom.id("spruce_sapling"), Block.SAPLING.getTexture(0, 1));
        BIRCH_SAPLING = new BroomSaplingBlock(Broom.id("birch_sapling"), Block.SAPLING.getTexture(0, 2));

        GRASS = new BroomTallPlantBlock(Broom.id("grass"), Block.GRASS.getTexture(0, 1));
        FERN = new BroomTallPlantBlock(Broom.id("fern"), Block.GRASS.getTexture(0, 2));

        PUMPKIN = new BroomPumpkinBlock(Broom.id("pumpkin"), Material.PUMPKIN);
        PUMPKIN_CROPS = new BroomPumpkinCropBlock(Broom.id("pumpkin_crop"), Block.WHEAT.textureId);

        STONE_SLAB = new BroomSlabBlock(Broom.id("stone_slab"), Block.STONE);
        STONE_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("stone_double_slab"), Block.STONE);

        SANDSTONE_SLAB = new BroomSlabBlock(Broom.id("sandstone_slab"), Block.SANDSTONE);
        SANDSTONE_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("sandstone_double_slab"), Block.SANDSTONE);

        WOODEN_SLAB = new BroomSlabBlock(Broom.id("wooden_slab"), Block.PLANKS);
        WOODEN_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("wooden_double_slab"), Block.PLANKS);

        COBBLESTONE_SLAB = new BroomSlabBlock(Broom.id("cobblestone_slab"), Block.COBBLESTONE);
        COBBLESTONE_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("cobblestone_double_slab"), Block.COBBLESTONE);

        BRICK_SLAB = new BroomSlabBlock(Broom.id("brick_slab"), Block.BRICKS);
        BRICK_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("brick_double_slab"), Block.BRICKS);
        BRICK_STAIRS = new TemplateStairsBlock(Broom.id("brick_stairs"), Block.BRICKS).setTranslationKey(Broom.id("brick_stairs"));

        STONE_BRICKS = new TemplateBlock(Broom.id("stone_bricks"), Material.STONE).setTranslationKey(Broom.id("stone_bricks")).setHardness(Block.STONE.getHardness()).setSoundGroup(Block.STONE_SOUND_GROUP);
        STONE_BRICK_SLAB = new BroomSlabBlock(Broom.id("stone_brick_slab"), BroomBlocks.STONE_BRICKS);
        STONE_BRICK_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("stone_brick_double_slab"), BroomBlocks.STONE_BRICKS);
        STONE_BRICK_STAIRS = new TemplateStairsBlock(Broom.id("stone_brick_stairs"), BroomBlocks.STONE_BRICKS).setTranslationKey(Broom.id("stone_brick_stairs"));

        WOODEN_BUTTON = new BroomButtonBlock(Broom.id("wooden_button"), Block.PLANKS, 30);

        IRON_TRAPDOOR = new TemplateTrapdoorBlock(Broom.id("iron_trapdoor"), Material.METAL).setTranslationKey(Broom.id("iron_trapdoor")).setHardness(5.0F).setSoundGroup(Block.METAL_SOUND_GROUP).disableTrackingStatistics().ignoreMetaUpdates();

        connectSlabs(STONE_SLAB, STONE_DOUBLE_SLAB);
        connectSlabs(SANDSTONE_SLAB, SANDSTONE_DOUBLE_SLAB);
        connectSlabs(WOODEN_SLAB, WOODEN_DOUBLE_SLAB);
        connectSlabs(COBBLESTONE_SLAB, COBBLESTONE_DOUBLE_SLAB);
        connectSlabs(BRICK_SLAB, BRICK_DOUBLE_SLAB);
        connectSlabs(STONE_BRICK_SLAB, STONE_BRICK_DOUBLE_SLAB);

        BonemealAPI.addPlant(Block.GRASS_BLOCK.getDefaultState(), new BroomGrassPatchFeature(), 10);
        BonemealAPI.addPlant(Block.GRASS_BLOCK.getDefaultState(), BroomBlocks.FERN.getDefaultState(), 1);
        BonemealAPI.addPlant(Block.GRASS_BLOCK.getDefaultState(), Block.DANDELION.getDefaultState(), 1);
        BonemealAPI.addPlant(Block.GRASS_BLOCK.getDefaultState(), Block.ROSE.getDefaultState(), 1);
    }

    /**
     * @author paulevsGitch
     */
    private static void connectSlabs(Block slab, Block doubleSlab) {
        ((BroomSlabBlock) slab).setDoubleSlabBlock(doubleSlab);
        ((BroomDoubleSlabBlock) doubleSlab).setSlabBlock(slab);
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

    public static Block getGrassByMeta(int meta) {
        if (meta == 2) {
            return FERN;
        } else {
            return GRASS;
        }
    }

    /**
     * @author paulevsGitch
     */
    public static Block getSlabByMeta(int meta) {
        return switch (meta & 3) {
            case 1 -> SANDSTONE_SLAB;
            case 2 -> WOODEN_SLAB;
            case 3 -> COBBLESTONE_SLAB;
            default -> STONE_SLAB;
        };
    }

    /**
     * @author paulevsGitch
     */
    public static Block getDoubleSlabByMeta(int meta) {
        return switch (meta & 3) {
            case 1 -> SANDSTONE_DOUBLE_SLAB;
            case 2 -> WOODEN_DOUBLE_SLAB;
            case 3 -> COBBLESTONE_DOUBLE_SLAB;
            default -> STONE_DOUBLE_SLAB;
        };
    }
}
