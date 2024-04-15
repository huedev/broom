package me.huedev.broom.mixin.common.block;

import me.huedev.broom.block.BroomBlockTags;
import me.huedev.broom.block.BroomBlocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collections;
import java.util.List;

@Mixin(SaplingBlock.class)
public class SaplingBlockMixin extends PlantBlock {
    public SaplingBlockMixin(int id, int textureId) {
        super(id, textureId);
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return this.canPlantOn(world.getBlockState(x, y - 1, z));
    }

    @Unique
    protected boolean canPlantOn(BlockState state) {
        return state.isIn(BroomBlockTags.DIRT);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        int meta = world.getBlockMeta(x, y, z);
        BlockState state = BroomBlocks.getSaplingByMeta(meta).getDefaultState();
        world.setBlockStateWithNotify(x, y, z, state);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        return Collections.singletonList(new ItemStack(BroomBlocks.getSaplingByMeta(meta)));
    }
}
