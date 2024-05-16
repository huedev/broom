package me.huedev.broom.mixin.common.block;

import me.huedev.broom.util.ToolHelper;
import me.huedev.broom.util.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.SnowyBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(SnowyBlock.class)
public class SnowyBlockMixin extends Block {
    @Unique
    private boolean brokenBySilkTouchTool = false;

    @Unique
    private boolean brokenByNonSilkTouchShovel = false;

    public SnowyBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Inject(method = "getCollisionShape", at = @At("RETURN"), cancellable = true)
    public void broom_getCollisionShape(World world, int x, int y, int z, CallbackInfoReturnable<Box> cir) {
        int layers = world.getBlockMeta(x, y, z);
        if (layers > 3) {
            Box box = Box.create((double) x + 0.0F, (double) y + 0.0F, (double) z + 0.0F, (double) x + 1.0F, (double) y + 0.5F, (double) z + 1.0F);
            cir.setReturnValue(box);
        } else {
            cir.setReturnValue(null);
        }

        /*
        int layers = world.getBlockMeta(x, y, z);
        float height = (float)(2 * layers) / 16.0F;
        Box box = Box.create((double) x + 0.0F, (double) y + 0.0F, (double) z + 0.0F, (double) x + 1.0F, (double) y + height, (double) z + 1.0F);
        cir.setReturnValue(box);
        */
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        ItemStack stack = player.getHand();
        if (stack == null) return false;

        Item item = stack.getItem();
        if (!(item instanceof BlockItem blockItem)) return false;

        if (blockItem.getBlock() != this) return false;

        int blockId = world.getBlockId(x, y, z);
        if (blockId != this.id) return false;

        HitResult hitResult = WorldHelper.raycast(world, player);
        if (hitResult.side != Direction.UP.getId()) return false;

        BlockState state = world.getBlockState(x, y, z);
        int meta = world.getBlockMeta(x, y, z);

        if (meta < 7) {
            meta += 1;
            world.setBlockStateWithMetadataWithNotify(x, y, z, state, meta);
            //world.method_215(x, y, z, meta);
            world.playSound(x + 0.5, y + 0.5, z + 0.5, this.soundGroup.getSound(), 1.0F, 1.0F);
            world.method_246(x, y, z);

            stack.count--;
        }
        return true;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (ToolHelper.isUsingGoldenTool(player)) {
            brokenBySilkTouchTool = true;
            brokenByNonSilkTouchShovel = false;
        } else if (ToolHelper.isUsingShovel(player)) {
            brokenByNonSilkTouchShovel = true;
            brokenBySilkTouchTool = false;
        }

        player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        this.dropStacks(world, x, y, z, meta);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        if (brokenBySilkTouchTool) {
            brokenBySilkTouchTool = false;
            brokenByNonSilkTouchShovel = false;
            return Collections.singletonList(new ItemStack(this.asItem(), meta + 1, 0));
        } else if (brokenByNonSilkTouchShovel) {
            brokenByNonSilkTouchShovel = false;
            return Collections.singletonList(new ItemStack(Item.SNOWBALL, meta + 1, 0));
        }
        return Collections.emptyList();
    }
}
