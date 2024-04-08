package me.huedev.broom.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.class_334;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Collections;
import java.util.List;

/**
 * @author paulevsGitch
 */
public class VanillaLeavesWrapper extends BroomLeavesBlock {
    private final int meta;

    public VanillaLeavesWrapper(Identifier id, int meta) {
        super(id);
        this.meta = meta;
    }

    @Override
    public int getTexture(int side, int meta) {
        return LEAVES.getTexture(side, this.meta);
    }

    @Override
    public int getTexture(int side) {
        return LEAVES.getTexture(side, this.meta);
    }

    @Override
    @Environment(value = EnvType.CLIENT)
    public int getColor(int meta) {
        return LEAVES.getColor(this.meta);
    }

    @Environment(value = EnvType.CLIENT)
    public int getColorMultiplier(BlockView view, int x, int y, int z) {
        if ((meta & 1) == 1) return class_334.method_1079();
        if ((meta & 2) == 2) return class_334.method_1082();
        view.method_1781().method_1788(x, z, 1, 1);
        double t = view.method_1781().field_2235[0];
        double w = view.method_1781().field_2236[0];
        return class_334.method_1080(t, w);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        int count = LEAVES.getDroppedItemCount(world.field_214);
        if (count == 0) return Collections.emptyList();
        return Collections.singletonList(new ItemStack(BroomBlocks.getSaplingByMeta(this.meta), count, 0));
    }
}
