package me.huedev.broom.block;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.template.block.TemplatePlantBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import org.spongepowered.asm.mixin.Unique;

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
        return state.isIn(BroomBlockTags.DIRT);
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        this.breakIfInvalid(world, x, y, z);
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        if (!world.isRemote) {
            this.breakIfInvalid(world, x, y, z);
            if (world.method_255(x, y + 1, z) >= 9 && random.nextInt(30) == 0) {
                BlockState state = world.getBlockState(x, y, z);
                int stage = state.get(BroomBlockProperties.SAPLING_STAGE);
                if (stage == 0) {
                    world.setBlockStateWithNotify(x, y, z, state.with(BroomBlockProperties.SAPLING_STAGE, 1));
                } else {
                    this.growTree(world, x, y, z, world.field_214);
                }
            }
        }
    }

    @Unique
    protected void breakIfInvalid(World world, int x, int y, int z) {
        if (!this.canGrow(world, x, y, z)) {
            this.dropStacks(world, x, y, z, world.getBlockMeta(x, y, z));
            world.setBlock(x, y, z, 0);
        }
    }

    @Override
    public boolean canGrow(World world, int x, int y, int z) {
        return (world.method_252(x, y, z) >= 8 || world.method_249(x, y, z)) && this.canPlantOn(world.getBlockState(x, y - 1, z));
    }

    @Override
    public boolean onBonemealUse(World world, int x, int y, int z, BlockState state) {
        if (!world.isRemote) {
            this.growTree(world, x, y, z, world.field_214);
        }
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
