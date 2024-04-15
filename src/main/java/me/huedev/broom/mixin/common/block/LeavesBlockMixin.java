package me.huedev.broom.mixin.common.block;

import me.huedev.broom.block.BroomBlocks;
import me.huedev.broom.util.ToolHelper;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.class_307;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author paulevsGitch
 */
@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends class_307 {
    @Unique
    private boolean brokenBySilkTouchTool = false;

    public LeavesBlockMixin(int i, int j, Material arg, boolean bl) {
        super(i, j, arg, bl);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        int meta = world.getBlockMeta(x, y, z);
        BlockState state = BroomBlocks.getLeavesByMeta(meta).getDefaultState();
        world.setBlockStateWithNotify(x, y, z, state);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (ToolHelper.isUsingSilkTouchTool(player) || ToolHelper.isUsingShears(player)) {
            brokenBySilkTouchTool = true;
        }

        this.dropStacks(world, x, y, z, meta);
    }

    @Inject(at = @At("RETURN"), method = "getDroppedItemCount", cancellable = true)
    public void broom_getDroppedItemCount(Random random, CallbackInfoReturnable<Integer> cir) {
        if (brokenBySilkTouchTool) {
            cir.setReturnValue(1);
        }
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        if (brokenBySilkTouchTool) {
            brokenBySilkTouchTool = false;
            return Collections.singletonList(new ItemStack(BroomBlocks.getLeavesByMeta(meta), 1, 0));
        } else {
            int count = this.getDroppedItemCount(world.field_214);
            if (count == 0) return Collections.emptyList();
            return Collections.singletonList(new ItemStack(BroomBlocks.getSaplingByMeta(meta), count, 0));
        }
    }
}
