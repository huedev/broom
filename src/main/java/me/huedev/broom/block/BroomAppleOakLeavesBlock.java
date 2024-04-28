package me.huedev.broom.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Collections;
import java.util.List;

public class BroomAppleOakLeavesBlock extends BroomLeavesBlock {
    public BroomAppleOakLeavesBlock(Identifier id) {
        super(id);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        BlockState state = world.getBlockState(x, y, z);
        boolean isNatural = state.get(BroomBlockProperties.NATURAL);
        boolean isActive = state.get(BroomBlockProperties.ACTIVE);
        BlockState newState = BroomBlocks.OAK_LEAVES.getDefaultState().with(BroomBlockProperties.NATURAL, isNatural);
        newState = newState.with(BroomBlockProperties.ACTIVE, isActive);
        world.setBlockStateWithNotify(x, y, z, newState);
        ItemStack apple = new ItemStack(Item.APPLE, 1);
        dropStack(world, x, y, z, apple);
        return true;
    }

    @Override
    @Environment(value = EnvType.CLIENT)
    public int getColor(int meta) {
        return LEAVES.getColor(0);
    }

    @Environment(value = EnvType.CLIENT)
    public int getColorMultiplier(BlockView view, int x, int y, int z) {
        view.method_1781().method_1788(x, z, 1, 1);
        double t = view.method_1781().field_2235[0];
        double w = view.method_1781().field_2236[0];
        return FoliageColors.getColor(t, w);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        if (brokenBySilkTouchTool) {
            brokenBySilkTouchTool = false;
            return Collections.singletonList(new ItemStack(BroomBlocks.APPLE_OAK_LEAVES, 1, 0));
        } else {
            List<ItemStack> dropList = new java.util.ArrayList<>(Collections.emptyList());
            int saplingCount = this.getDroppedItemCount(world.field_214);
            if (saplingCount != 0) {
                dropList.add(new ItemStack(BroomBlocks.OAK_SAPLING, 1, 0));
            }
            dropList.add(new ItemStack(Item.APPLE, 1, 0));
            return dropList;
        }
    }
}
