package net.huedev.broom.mixin.common.block;

import net.huedev.broom.block.BroomBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin extends Block {
    public SugarCaneBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        BlockState state = world.getBlockState(x, y - 1, z);
        if (state.getBlock() == this) {
            return true;
        } else if (!state.isIn(BroomBlockTags.SUGAR_CANE_PLANTABLE_ON)) {
            return false;
        } else if (world.getMaterial(x - 1, y - 1, z) == Material.WATER) {
            return true;
        } else if (world.getMaterial(x + 1, y - 1, z) == Material.WATER) {
            return true;
        } else if (world.getMaterial(x, y - 1, z - 1) == Material.WATER) {
            return true;
        } else {
            return world.getMaterial(x, y - 1, z + 1) == Material.WATER;
        }
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        this.breakIfInvalid(world, x, y, z);
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
        return (world.getBrightness(x, y, z) >= 8 || world.hasSkyLight(x, y, z)) && this.canPlaceAt(world, x, y, z);
    }
}
