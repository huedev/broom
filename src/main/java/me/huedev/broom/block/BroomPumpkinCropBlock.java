package me.huedev.broom.block;

import me.huedev.broom.item.BroomItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.template.block.TemplatePlantBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BroomPumpkinCropBlock extends TemplatePlantBlock {
    public BroomPumpkinCropBlock(Identifier id, int textureId) {
        super(id, textureId);
        setTranslationKey(id.toString());
        setHardness(0.0F);
        setSoundGroup(DIRT_SOUND_GROUP);
        disableTrackingStatistics();
        ignoreMetaUpdates();
        float halfFaceLength = 0.5F;
        //this.setBoundingBox(0.5F - halfFaceLength, 0.0F, 0.5F - halfFaceLength, 0.5F + halfFaceLength, 0.25F, 0.5F + halfFaceLength);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BroomBlockProperties.CROP_AGE);
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return this.canPlantOn(world.getBlockState(x, y - 1, z));
    }

    protected boolean canPlantOn(BlockState state) {
        return state.getBlock().id == Block.FARMLAND.id;
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
                int age = state.get(BroomBlockProperties.CROP_AGE);
                if (age < 7) {
                    int incrementedAge = age + 1;
                    if (incrementedAge == 7) {
                        this.growCrop(world, x, y, z);
                    } else {
                        world.setBlockStateWithNotify(x, y, z, state.with(BroomBlockProperties.CROP_AGE, incrementedAge));
                    }
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
            //this.growCropBlock(world, x, y, z);
            int age = state.get(BroomBlockProperties.CROP_AGE);
            if (age < 7) {
                int incrementedAge = age + 1;
                if (incrementedAge == 7) {
                    this.growCrop(world, x, y, z);
                } else {
                    world.setBlockStateWithNotify(x, y, z, state.with(BroomBlockProperties.CROP_AGE, incrementedAge));
                }
            }
        }
        return true;
    }

    public void growCrop(World world, int x, int y, int z) {
        BlockState newState = BroomBlocks.PUMPKIN.getDefaultState();
        world.setBlockStateWithNotify(x, y, z, newState);
    }

    private Box generateBox(int x, int y, int z, int age) {
        float yOffset = 0.0625F;
        float startingLength = 0.25F;
        float halfStartingLength = 0.1875F;
        float increasedLengthPerAge = 0.125F;
        float halfIncreasedLengthPerAge = 0.0625F;
        float faceLength = startingLength + ((age - 3) * increasedLengthPerAge);
        float halfFaceLength = halfStartingLength + ((age - 3) * halfIncreasedLengthPerAge);
        return Box.create(
                (double) x + 0.5F - halfFaceLength,
                (double) y - yOffset,
                (double) z + 0.5F - halfFaceLength,
                (double) x + 0.5F + halfFaceLength,
                (double) y + faceLength + yOffset,
                (double) z + 0.5F + halfFaceLength);
    }

    @Override
    public Box getCollisionShape(World world, int x, int y, int z) {
        BlockState state = world.getBlockState(x, y, z);
        if (state.isOf(this)) {
            int age = state.get(BroomBlockProperties.CROP_AGE);
            if (age <= 2) {
                return null;
            } else {
                return generateBox(x, y, z, age);
            }
        }
        return null;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Box getBoundingBox(World world, int x, int y, int z) {
        BlockState state = world.getBlockState(x, y, z);
        if (state.isOf(this)) {
            int age = state.get(BroomBlockProperties.CROP_AGE);
            if (age <= 2) {
                return generateBox(x, y, z, 3);
            } else {
                return generateBox(x, y, z, age);
            }
        }
        float halfFaceLength = 0.5F;
        return Box.create(0.5F - halfFaceLength, 0.0F, 0.5F - halfFaceLength, 0.5F + halfFaceLength, 0.25F, 0.5F + halfFaceLength);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        int age = state.get(BroomBlockProperties.CROP_AGE);
        if (age < 7) {
            return Collections.singletonList(new ItemStack(BroomItems.PUMPKIN_SEEDS, 1));
        } else {
            return Collections.singletonList(new ItemStack(BroomBlocks.PUMPKIN.asItem(), 1));
        }
    }
}
