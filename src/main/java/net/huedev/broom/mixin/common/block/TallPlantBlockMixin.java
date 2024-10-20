package net.huedev.broom.mixin.common.block;

import net.huedev.broom.block.BroomBlockTags;
import net.huedev.broom.block.BroomBlocks;
import net.huedev.broom.util.ToolHelper;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin(TallPlantBlock.class)
public abstract class TallPlantBlockMixin extends PlantBlock {
    @Shadow public abstract int getDroppedItemId(int blockMeta, Random random);

    @Unique
    private boolean brokenByShears = false;

    public TallPlantBlockMixin(int id, int textureId) {
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
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (ToolHelper.isUsingShears(player)) {
            brokenByShears = true;
            player.inventory.getSelectedItem().damage(1, player);
        }

        if (player != null) {
            player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
            this.dropStacks(world, x, y, z, meta);
        }
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        if (brokenByShears) {
            return Collections.singletonList(new ItemStack(BroomBlocks.getGrassByMeta(meta)));
        } else {
            int id = this.getDroppedItemId(meta, world.field_214);
            int count = this.getDroppedItemCount(world.field_214);
            if (id == -1) return Collections.emptyList();
            return Collections.singletonList(new ItemStack(id, count, 0));
        }
    }
}
