package net.huedev.broom.mixin.common.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.huedev.broom.block.BroomBlockTags;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public class BlockMixin {
    @ModifyExpressionValue(method = "canPlaceAt(Lnet/minecraft/world/World;III)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Material;method_896()Z"))
    private boolean bypassMaterialReplaceableCheck(boolean original, @Local(argsOnly = true) World world, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        BlockState state = world.getBlockState(x, y, z);
        if (state.isIn(BroomBlockTags.REPLACEABLE)) {
            return true;
        } else {
            return original;
        }
    }
}
