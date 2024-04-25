package me.huedev.broom.mixin.common.item;

import me.huedev.broom.block.BroomBlocks;
import me.huedev.broom.item.BroomItems;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ShearsItem.class)
public class ShearsItemMixin extends Item {
    public ShearsItemMixin(int id) {
        super(id);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {
        int targetBlock = world.getBlockId(x, y, z);
        if (targetBlock == BroomBlocks.PUMPKIN.id) {
            BlockState state = Block.PUMPKIN.getDefaultState();
            Direction direction = Direction.byId(side);
            int meta = 0;
            if (direction.getHorizontal() != -1) {
                meta = direction.getHorizontal();
            } else {
                meta = MathHelper.floor((double)(player.yaw * 4.0F / 360.0F) + 2.5) & 3;
            }
            world.setBlockStateWithMetadataWithNotify(x, y, z, state, meta);
            if (!world.isRemote) {
                world.method_173(null, 1008, x, y, z, 0);
                ItemStack seeds = new ItemStack(BroomItems.PUMPKIN_SEEDS, 4);
                broom_dropStack(world, x, y, z, seeds);
                stack.damage(1, player);
            }
            return true;
        }
        return false;
    }

    @Unique
    private void broom_dropStack(World world, int x, int y, int z, ItemStack itemStack) {
        float var6 = 0.7F;
        double var7 = (double)(world.field_214.nextFloat() * var6) + (double)(1.0F - var6) * 0.5;
        double var9 = (double)(world.field_214.nextFloat() * var6) + (double)(1.0F - var6) * 0.5;
        double var11 = (double)(world.field_214.nextFloat() * var6) + (double)(1.0F - var6) * 0.5;
        ItemEntity var13 = new ItemEntity(world, (double)x + var7, (double)y + var9, (double)z + var11, itemStack);
        var13.pickupDelay = 10;
        world.method_210(var13);
    }
}
