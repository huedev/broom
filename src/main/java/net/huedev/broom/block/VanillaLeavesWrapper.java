package net.huedev.broom.block;

import net.huedev.broom.util.ToolHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.entity.player.PlayerEntity;
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
    private boolean brokenBySilkTouchTool = false;

    public VanillaLeavesWrapper(Identifier id, int meta) {
        super(id);
        this.meta = meta;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (!world.isRemote) {
            if (ToolHelper.isUsingGoldenTool(player) || ToolHelper.isUsingShears(player)) {
                brokenBySilkTouchTool = true;
            }

            this.dropStacks(world, x, y, z, meta);
        }
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
        if ((meta & 1) == 1) return FoliageColors.getSpruceColor();
        if ((meta & 2) == 2) return FoliageColors.getBirchColor();
        view.method_1781().getBiomesInArea(x, z, 1, 1);
        double t = view.method_1781().temperatureMap[0];
        double w = view.method_1781().downfallMap[0];
        return FoliageColors.getColor(t, w);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        if (brokenBySilkTouchTool) {
            brokenBySilkTouchTool = false;
            return Collections.singletonList(new ItemStack(BroomBlocks.getLeavesByMeta(meta), 1, 0));
        } else {
            int count = this.getDroppedItemCount(world.random);
            if (count == 0) return Collections.emptyList();
            return Collections.singletonList(new ItemStack(BroomBlocks.getSaplingByMeta(meta), count, 0));
        }
    }
}
