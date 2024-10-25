package net.huedev.broom.mixin.common.block;

import net.huedev.broom.block.BroomBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collections;
import java.util.List;

/**
 * @author paulevsGitch
 */
@Mixin(LogBlock.class)
public class LogBlockMixin extends Block {
    public LogBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        int meta = world.getBlockMeta(x, y, z);
        BlockState state = BroomBlocks.getLogByMeta(meta).getDefaultState();
        world.setBlockStateWithNotify(x, y, z, state);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        return Collections.singletonList(new ItemStack(BroomBlocks.getLogByMeta(meta)));
    }
}
