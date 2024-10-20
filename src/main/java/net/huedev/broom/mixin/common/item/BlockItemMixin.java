package net.huedev.broom.mixin.common.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.huedev.broom.block.BroomBlockTags;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockItem.class)
public class BlockItemMixin {
    @WrapOperation(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockId(III)I"))
    private int bypassSnowCheckIfPlacingSnow(World world, int x, int y, int z, Operation<Integer> original, @Local(argsOnly = true) ItemStack stack) {
        if (stack.itemId == Block.SNOW.asItem().id) {
            return 0;
        }

        BlockState state = world.getBlockState(x, y, z);
        if (state.isIn(BroomBlockTags.REPLACEABLE) && stack.itemId != state.getBlock().id) {
            return Block.SNOW.id;
        }

        return original.call(world, x, y, z);
    }
}
