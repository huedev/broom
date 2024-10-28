package net.huedev.broom.block;

import net.huedev.broom.Broom;
import net.huedev.broom.world.gen.feature.BroomGrassPatchFeature;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.bonemeal.BonemealAPI;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.template.block.TemplateStairsBlock;
import net.modificationstation.stationapi.api.template.block.TemplateTrapdoorBlock;

public class BroomBlocks {
    public static Block OAK_LOG;
    public static Block SPRUCE_LOG;
    public static Block BIRCH_LOG;
    public static Block CACAO_LOG;
    public static Block OAK_BARK;
    public static Block SPRUCE_BARK;
    public static Block BIRCH_BARK;
    public static Block CACAO_BARK;
    public static Block OAK_LEAVES;
    public static Block OAK_APPLE_LEAVES;
    public static Block SPRUCE_LEAVES;
    public static Block BIRCH_LEAVES;
    public static Block CACAO_LEAVES;
    public static Block CACAO_POD_LEAVES;
    public static Block OAK_SAPLING;
    public static Block SPRUCE_SAPLING;
    public static Block BIRCH_SAPLING;
    public static Block CACAO_SAPLING;
    public static Block GRASS;
    public static Block FERN;
    public static Block PUMPKIN;
    public static Block PUMPKIN_CROPS;
    public static Block WET_SPONGE;
    public static Block STONE_SLAB;
    public static Block STONE_DOUBLE_SLAB;
    public static Block STONE_STAIRS;
    public static Block STONE_BRICKS;
    public static Block STONE_BRICK_SLAB;
    public static Block STONE_BRICK_DOUBLE_SLAB;
    public static Block STONE_BRICK_STAIRS;
    public static Block POLISHED_STONE;
    public static Block POLISHED_STONE_SLAB;
    public static Block POLISHED_STONE_DOUBLE_SLAB;
    public static Block SANDSTONE_SLAB;
    public static Block SANDSTONE_DOUBLE_SLAB;
    public static Block WOODEN_SLAB;
    public static Block WOODEN_DOUBLE_SLAB;
    public static Block COBBLESTONE_SLAB;
    public static Block COBBLESTONE_DOUBLE_SLAB;
    public static Block MOSSY_COBBLESTONE_SLAB;
    public static Block MOSSY_COBBLESTONE_DOUBLE_SLAB;
    public static Block MOSSY_COBBLESTONE_STAIRS;
    public static Block BRICK_SLAB;
    public static Block BRICK_DOUBLE_SLAB;
    public static Block BRICK_STAIRS;
    public static Block POLISHED_STONE_BRICKS;
    public static Block POLISHED_STONE_BRICK_SLAB;
    public static Block POLISHED_STONE_BRICK_DOUBLE_SLAB;
    public static Block POLISHED_STONE_BRICK_STAIRS;
    public static Block SANDSTONE_STAIRS;
    public static Block SANDSTONE_BRICKS;
    public static Block SANDSTONE_BRICK_SLAB;
    public static Block SANDSTONE_BRICK_DOUBLE_SLAB;
    public static Block SANDSTONE_BRICK_STAIRS;
    public static Block SNOW_BRICKS;
    public static Block SNOW_BRICK_SLAB;
    public static Block SNOW_BRICK_DOUBLE_SLAB;
    public static Block SNOW_BRICK_STAIRS;
    public static Block WHITE_WOOL;
    public static Block ORANGE_WOOL;
    public static Block MAGENTA_WOOL;
    public static Block LIGHT_BLUE_WOOL;
    public static Block YELLOW_WOOL;
    public static Block LIME_WOOL;
    public static Block PINK_WOOL;
    public static Block GRAY_WOOL;
    public static Block LIGHT_GRAY_WOOL;
    public static Block CYAN_WOOL;
    public static Block PURPLE_WOOL;
    public static Block BLUE_WOOL;
    public static Block BROWN_WOOL;
    public static Block GREEN_WOOL;
    public static Block RED_WOOL;
    public static Block BLACK_WOOL;
    public static Block WOODEN_BUTTON;
    public static Block FENCE_GATE;
    public static Block IRON_TRAPDOOR;
    public static Block GLOWSTONE_LAMP;
    public static Block COAL_BLOCK;
    public static Block CHARCOAL_BLOCK;
    public static Block REDSTONE_BLOCK;
    public static Block HAY_BALE;

    public static void init() {
        OAK_LOG = new BroomLogBlock(Broom.id("oak_log"));
        SPRUCE_LOG = new BroomLogBlock(Broom.id("spruce_log"));
        BIRCH_LOG = new BroomLogBlock(Broom.id("birch_log"));
        CACAO_LOG = new BroomLogBlock(Broom.id("cacao_log"));

        OAK_BARK = new BroomLogBlock(Broom.id("oak_bark"));
        SPRUCE_BARK = new BroomLogBlock(Broom.id("spruce_bark"));
        BIRCH_BARK = new BroomLogBlock(Broom.id("birch_bark"));
        CACAO_BARK = new BroomLogBlock(Broom.id("cacao_bark"));

        OAK_LEAVES = new VanillaLeavesWrapper(Broom.id("oak_leaves"), 0);
        OAK_APPLE_LEAVES = new BroomLeavesBlock(Broom.id("oak_apple_leaves"));
        SPRUCE_LEAVES = new VanillaLeavesWrapper(Broom.id("spruce_leaves"), 1);
        BIRCH_LEAVES = new VanillaLeavesWrapper(Broom.id("birch_leaves"), 2);
        CACAO_LEAVES = new BroomLeavesBlock(Broom.id("cacao_leaves"));
        CACAO_POD_LEAVES = new BroomLeavesBlock(Broom.id("cacao_pod_leaves"));

        OAK_SAPLING = new BroomSaplingBlock(Broom.id("oak_sapling"), Block.SAPLING.getTexture(0, 0));
        SPRUCE_SAPLING = new BroomSaplingBlock(Broom.id("spruce_sapling"), Block.SAPLING.getTexture(0, 1));
        BIRCH_SAPLING = new BroomSaplingBlock(Broom.id("birch_sapling"), Block.SAPLING.getTexture(0, 2));
        CACAO_SAPLING = new BroomSaplingBlock(Broom.id("cacao_sapling"), Block.SAPLING.getTexture(0, 0));

        GRASS = new BroomTallPlantBlock(Broom.id("grass"), Block.GRASS.getTexture(0, 1));
        FERN = new BroomTallPlantBlock(Broom.id("fern"), Block.GRASS.getTexture(0, 2));

        PUMPKIN = new BroomPumpkinBlock(Broom.id("pumpkin"), Material.PUMPKIN);
        PUMPKIN_CROPS = new BroomPumpkinCropBlock(Broom.id("pumpkin_crop"), Block.WHEAT.textureId);

        WET_SPONGE = new BroomWetSpongeBlock(Broom.id("wet_sponge"), Material.SPONGE);

        STONE_SLAB = new BroomSlabBlock(Broom.id("stone_slab"), Block.STONE);
        STONE_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("stone_double_slab"), Block.STONE);
        STONE_STAIRS = new TemplateStairsBlock(Broom.id("stone_stairs"), Block.STONE)
                .setTranslationKey(Broom.id("stone_stairs"));

        STONE_BRICKS = new TemplateBlock(Broom.id("stone_bricks"), Material.STONE)
                .setTranslationKey(Broom.id("stone_bricks"))
                .setHardness(Block.STONE.getHardness())
                .setSoundGroup(Block.STONE_SOUND_GROUP);
        STONE_BRICK_SLAB = new BroomSlabBlock(Broom.id("stone_brick_slab"), BroomBlocks.STONE_BRICKS);
        STONE_BRICK_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("stone_brick_double_slab"), BroomBlocks.STONE_BRICKS);
        STONE_BRICK_STAIRS = new TemplateStairsBlock(Broom.id("stone_brick_stairs"), BroomBlocks.STONE_BRICKS)
                .setTranslationKey(Broom.id("stone_brick_stairs"));

        POLISHED_STONE = new TemplateBlock(Broom.id("polished_stone"), Material.STONE)
                .setTranslationKey(Broom.id("polished_stone"))
                .setHardness(Block.STONE.getHardness())
                .setSoundGroup(Block.STONE_SOUND_GROUP);
        POLISHED_STONE_SLAB = new BroomSlabBlock(Broom.id("polished_stone_slab"), BroomBlocks.POLISHED_STONE);
        POLISHED_STONE_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("polished_stone_double_slab"), BroomBlocks.POLISHED_STONE);

        SANDSTONE_SLAB = new BroomSlabBlock(Broom.id("sandstone_slab"), Block.SANDSTONE);
        SANDSTONE_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("sandstone_double_slab"), Block.SANDSTONE);

        WOODEN_SLAB = new BroomSlabBlock(Broom.id("wooden_slab"), Block.PLANKS);
        WOODEN_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("wooden_double_slab"), Block.PLANKS);

        COBBLESTONE_SLAB = new BroomSlabBlock(Broom.id("cobblestone_slab"), Block.COBBLESTONE);
        COBBLESTONE_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("cobblestone_double_slab"), Block.COBBLESTONE);

        MOSSY_COBBLESTONE_SLAB = new BroomSlabBlock(Broom.id("mossy_cobblestone_slab"), Block.MOSSY_COBBLESTONE);
        MOSSY_COBBLESTONE_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("mossy_cobblestone_double_slab"), Block.MOSSY_COBBLESTONE);
        MOSSY_COBBLESTONE_STAIRS = new TemplateStairsBlock(Broom.id("mossy_cobblestone_stairs"), Block.MOSSY_COBBLESTONE)
                .setTranslationKey(Broom.id("mossy_cobblestone_stairs"));

        BRICK_SLAB = new BroomSlabBlock(Broom.id("brick_slab"), Block.BRICKS);
        BRICK_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("brick_double_slab"), Block.BRICKS);
        BRICK_STAIRS = new TemplateStairsBlock(Broom.id("brick_stairs"), Block.BRICKS)
                .setTranslationKey(Broom.id("brick_stairs"));

        POLISHED_STONE_BRICKS = new TemplateBlock(Broom.id("polished_stone_bricks"), Material.STONE)
                .setTranslationKey(Broom.id("polished_stone_bricks"))
                .setHardness(Block.STONE.getHardness())
                .setSoundGroup(Block.STONE_SOUND_GROUP);
        POLISHED_STONE_BRICK_SLAB = new BroomSlabBlock(Broom.id("polished_stone_brick_slab"), BroomBlocks.POLISHED_STONE_BRICKS);
        POLISHED_STONE_BRICK_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("polished_stone_brick_double_slab"), BroomBlocks.POLISHED_STONE_BRICKS);
        POLISHED_STONE_BRICK_STAIRS = new TemplateStairsBlock(Broom.id("polished_stone_brick_stairs"), BroomBlocks.POLISHED_STONE_BRICKS)
                .setTranslationKey(Broom.id("polished_stone_brick_stairs"));

        SANDSTONE_STAIRS = new TemplateStairsBlock(Broom.id("sandstone_stairs"), Block.SANDSTONE)
                .setTranslationKey(Broom.id("sandstone_stairs"));

        SANDSTONE_BRICKS = new TemplateBlock(Broom.id("sandstone_bricks"), Material.STONE)
                .setTranslationKey(Broom.id("sandstone_bricks"))
                .setHardness(Block.SANDSTONE.getHardness())
                .setSoundGroup(Block.STONE_SOUND_GROUP);
        SANDSTONE_BRICK_SLAB = new BroomSlabBlock(Broom.id("sandstone_brick_slab"), BroomBlocks.SANDSTONE_BRICKS);
        SANDSTONE_BRICK_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("sandstone_brick_double_slab"), BroomBlocks.SANDSTONE_BRICKS);
        SANDSTONE_BRICK_STAIRS = new TemplateStairsBlock(Broom.id("sandstone_brick_stairs"), BroomBlocks.SANDSTONE_BRICKS)
                .setTranslationKey(Broom.id("sandstone_brick_stairs"));

        SNOW_BRICKS = new TemplateBlock(Broom.id("snow_bricks"), Material.SNOW_BLOCK)
                .setTranslationKey(Broom.id("snow_bricks"))
                .setHardness(0.3F)
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        SNOW_BRICK_SLAB = new BroomSlabBlock(Broom.id("snow_brick_slab"), BroomBlocks.SNOW_BRICKS);
        SNOW_BRICK_DOUBLE_SLAB = new BroomDoubleSlabBlock(Broom.id("snow_brick_double_slab"), BroomBlocks.SNOW_BRICKS);
        SNOW_BRICK_STAIRS = new TemplateStairsBlock(Broom.id("snow_brick_stairs"), BroomBlocks.SNOW_BRICKS)
                .setTranslationKey(Broom.id("snow_brick_stairs"));

        WHITE_WOOL = new TemplateBlock(Broom.id("white_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("white_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        ORANGE_WOOL = new TemplateBlock(Broom.id("orange_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("orange_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        MAGENTA_WOOL = new TemplateBlock(Broom.id("magenta_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("magenta_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        LIGHT_BLUE_WOOL = new TemplateBlock(Broom.id("light_blue_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("light_blue_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        YELLOW_WOOL = new TemplateBlock(Broom.id("yellow_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("yellow_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        LIME_WOOL = new TemplateBlock(Broom.id("lime_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("lime_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        PINK_WOOL = new TemplateBlock(Broom.id("pink_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("pink_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        GRAY_WOOL = new TemplateBlock(Broom.id("gray_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("gray_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        LIGHT_GRAY_WOOL = new TemplateBlock(Broom.id("light_gray_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("light_gray_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        CYAN_WOOL = new TemplateBlock(Broom.id("cyan_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("cyan_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        PURPLE_WOOL = new TemplateBlock(Broom.id("purple_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("purple_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        BLUE_WOOL = new TemplateBlock(Broom.id("blue_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("blue_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        BROWN_WOOL = new TemplateBlock(Broom.id("brown_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("brown_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        GREEN_WOOL = new TemplateBlock(Broom.id("green_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("green_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        RED_WOOL = new TemplateBlock(Broom.id("red_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("red_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);
        BLACK_WOOL = new TemplateBlock(Broom.id("black_wool"), Material.WOOL)
                .setTranslationKey(Broom.id("black_wool"))
                .setHardness(Block.WOOL.getHardness())
                .setSoundGroup(Block.WOOL_SOUND_GROUP);

        WOODEN_BUTTON = new BroomButtonBlock(Broom.id("wooden_button"), Block.PLANKS, 30);

        FENCE_GATE = new BroomFenceGateBlock(Broom.id("fence_gate"));

        IRON_TRAPDOOR = new TemplateTrapdoorBlock(Broom.id("iron_trapdoor"), Material.METAL)
                .setTranslationKey(Broom.id("iron_trapdoor"))
                .setHardness(5.0F)
                .setSoundGroup(Block.METAL_SOUND_GROUP)
                .disableTrackingStatistics()
                .ignoreMetaUpdates();

        GLOWSTONE_LAMP = new BroomGlowstoneLampBlock(Broom.id("glowstone_lamp"));

        COAL_BLOCK = new TemplateBlock(Broom.id("coal_block"), Material.STONE)
                .setTranslationKey(Broom.id("coal_block"))
                .setHardness(5.0F)
                .setSoundGroup(Block.STONE_SOUND_GROUP);
        CHARCOAL_BLOCK = new TemplateBlock(Broom.id("charcoal_block"), Material.STONE)
                .setTranslationKey(Broom.id("charcoal_block"))
                .setHardness(5.0F)
                .setSoundGroup(Block.STONE_SOUND_GROUP);
        REDSTONE_BLOCK = new BroomRedstoneBlock(Broom.id("redstone_block"), Material.METAL);

        HAY_BALE = new BroomHayBaleBlock(Broom.id("hay_bale"));

        connectSlabs(POLISHED_STONE_SLAB, POLISHED_STONE_DOUBLE_SLAB);
        connectSlabs(SANDSTONE_SLAB, SANDSTONE_DOUBLE_SLAB);
        connectSlabs(WOODEN_SLAB, WOODEN_DOUBLE_SLAB);
        connectSlabs(COBBLESTONE_SLAB, COBBLESTONE_DOUBLE_SLAB);
        connectSlabs(MOSSY_COBBLESTONE_SLAB, MOSSY_COBBLESTONE_DOUBLE_SLAB);
        connectSlabs(BRICK_SLAB, BRICK_DOUBLE_SLAB);
        connectSlabs(STONE_SLAB, STONE_DOUBLE_SLAB);
        connectSlabs(STONE_BRICK_SLAB, STONE_BRICK_DOUBLE_SLAB);
        connectSlabs(POLISHED_STONE_BRICK_SLAB, POLISHED_STONE_BRICK_DOUBLE_SLAB);
        connectSlabs(SANDSTONE_BRICK_SLAB, SANDSTONE_BRICK_DOUBLE_SLAB);
        connectSlabs(SNOW_BRICK_SLAB, SNOW_BRICK_DOUBLE_SLAB);

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
            default -> POLISHED_STONE_SLAB;
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
            default -> POLISHED_STONE_DOUBLE_SLAB;
        };
    }

    public static Block getWoolFromColor(int color) {
        return switch (color) {
            case 0 -> WHITE_WOOL;
            case 1 -> ORANGE_WOOL;
            case 2 -> MAGENTA_WOOL;
            case 3 -> LIGHT_BLUE_WOOL;
            case 4 -> YELLOW_WOOL;
            case 5 -> LIME_WOOL;
            case 6 -> PINK_WOOL;
            case 7 -> GRAY_WOOL;
            case 8 -> LIGHT_GRAY_WOOL;
            case 9 -> CYAN_WOOL;
            case 10 -> PURPLE_WOOL;
            case 11 -> BLUE_WOOL;
            case 12 -> BROWN_WOOL;
            case 13 -> GREEN_WOOL;
            case 14 -> RED_WOOL;
            case 15 -> BLACK_WOOL;
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
    }
}
