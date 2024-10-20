package net.huedev.broom.mixin.common.block;

import net.huedev.broom.block.BroomBlockTags;
import net.huedev.broom.util.ToolHelper;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(DeadBushBlock.class)
public class DeadBushBlockMixin extends PlantBlock {
    @Unique
    private boolean brokenByShears = false;

    public DeadBushBlockMixin(int id, int textureId) {
        super(id, textureId);
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return this.canPlantOn(world.getBlockState(x, y - 1, z));
    }

    @Unique
    protected boolean canPlantOn(BlockState state) {
        return state.isIn(BroomBlockTags.DEAD_BUSH_PLANTABLE_ON);
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
        return (world.method_252(x, y, z) >= 8 || world.method_249(x, y, z)) && this.canPlantOn(world.getBlockState(x, y - 1, z));
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
    public int getDroppedItemCount(Random random) {
        if (brokenByShears) {
            return 1;
        }
        return random.nextInt(3);
    }

    @Inject(at = @At("RETURN"), method = "getDroppedItemId", cancellable = true)
    public void broom_getDroppedItemId(int blockMeta, Random random, CallbackInfoReturnable<Integer> cir) {
        if (brokenByShears) {
            cir.setReturnValue(this.id);
            brokenByShears = false;
        } else {
            cir.setReturnValue(Item.STICK.id);
        }
    }
}
