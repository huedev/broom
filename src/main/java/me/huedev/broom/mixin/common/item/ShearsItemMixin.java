package me.huedev.broom.mixin.common.item;

import me.huedev.broom.block.BroomBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShearsItem.class)
public class ShearsItemMixin extends Item {
    public ShearsItemMixin(int id) {
        super(id);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {
        int targetBlock = world.getBlockId(x, y, z);
        if (targetBlock == BroomBlocks.PUMPKIN.id) {
            Block newBlock = Block.PUMPKIN;
            BlockState state = newBlock.getDefaultState();
            Direction direction = Direction.byId(side);
            int meta = 0;
            if (direction.getHorizontal() != -1) {
                meta = direction.getHorizontal();
            } else {
                meta = MathHelper.floor((double)(player.yaw * 4.0F / 360.0F) + 2.5) & 3;
            }
            world.setBlockStateWithMetadataWithNotify(x, y, z, state, meta);
            world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "broom:entity.sheep.shear", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            stack.damage(1, player);
            return true;
        }
        return false;
    }
}
