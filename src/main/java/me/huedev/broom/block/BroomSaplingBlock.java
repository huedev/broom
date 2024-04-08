package me.huedev.broom.block;

import me.huedev.broom.util.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.template.block.TemplatePlantBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class BroomSaplingBlock extends TemplatePlantBlock {
    public BroomSaplingBlock(Identifier id, int texture) {
        super(id, texture);
        setTranslationKey(id.toString());
        setSoundGroup(DIRT_SOUND_GROUP);
        float halfFaceLength = 0.4F;
        this.setBoundingBox(0.5F - halfFaceLength, 0.0F, 0.5F - halfFaceLength, 0.5F + halfFaceLength, halfFaceLength * 2.0F, 0.5F + halfFaceLength);
        setDefaultState(getDefaultState().with(BroomBlockProperties.SAPLING_STAGE, 0));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BroomBlockProperties.SAPLING_STAGE);
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return this.canPlantOn(world.getBlockState(x, y - 1, z));
    }

    protected boolean canPlantOn(BlockState state) {
        return state.isIn(BroomBlockTags.SAPLING_PLANTABLE_ON);
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        if (!world.isRemote) {
            super.onTick(world, x, y, z, random);
            if (world.method_255(x, y + 1, z) >= 9 && random.nextInt(30) == 0) {
                BlockState state = world.getBlockState(x, y, z);
                int stage = state.get(BroomBlockProperties.SAPLING_STAGE);
                if (stage == 0) {
                    WorldHelper.setBlockSilent(world, x, y, z, state.with(BroomBlockProperties.SAPLING_STAGE, 1));
                } else {
                    this.growTree(world, x, y, z, world.field_214);
                }
            }
        }
    }

    @Override
    public boolean onBonemealUse(World world, int x, int y, int z, BlockState state) {
        this.growTree(world, x, y, z, world.field_214);
        return true;
    }

    public void growTree(World world, int x, int y, int z, Random random) {
        world.method_200(x, y, z, 0);
        Feature feature;
        if (this.id == BroomBlocks.SPRUCE_SAPLING.id) {
            feature = new SpruceTreeFeature();
            if (random.nextInt(2) == 0) {
                feature = new PineTreeFeature();
            }
        } else if (this.id == BroomBlocks.BIRCH_SAPLING.id) {
            feature = new BirchTreeFeature();
        } else {
            feature = new OakTreeFeature();
            if (random.nextInt(10) == 0) {
                feature = new LargeOakTreeFeature();
            }
        }

        if (!feature.generate(world, random, x, y, z)) {
            world.method_154(x, y, z, this.id, this.id);
        }
    }
}
