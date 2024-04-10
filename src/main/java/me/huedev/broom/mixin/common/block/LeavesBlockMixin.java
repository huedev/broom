package me.huedev.broom.mixin.common.block;

import me.huedev.broom.block.BroomBlocks;
import me.huedev.broom.util.WorldHelper;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.class_307;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collections;
import java.util.List;

/**
 * @author paulevsGitch
 */
@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends class_307 {
    public LeavesBlockMixin(int i, int j, Material arg, boolean bl) {
        super(i, j, arg, bl);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        int meta = world.getBlockMeta(x, y, z);
        BlockState state = BroomBlocks.getLeavesByMeta(meta).getDefaultState();
        WorldHelper.setBlockSilent(world, x, y, z, state);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        int count = this.getDroppedItemCount(world.field_214);
        if (count == 0) return Collections.emptyList();
        return Collections.singletonList(new ItemStack(BroomBlocks.getSaplingByMeta(meta), count, 0));
    }
}
