package net.huedev.broom.mixin.common.item;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.huedev.broom.util.WorldHelper;
import net.minecraft.block.BedBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedItem.class)
public class BedItemMixin {
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;method_201(IIIII)Z", ordinal = 0))
    private void broom_playBlockPlaceSound(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side, CallbackInfoReturnable<Boolean> cir, @Local BedBlock var8) {
        world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), var8.soundGroup.getSound(), (var8.soundGroup.method_1976() + 1.0F) / 2.0F, var8.soundGroup.method_1977() * 0.8F);
    }

    @ModifyExpressionValue(
            method = "useOnBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;method_1780(III)Z",
                    ordinal = 0
            )
    )
    private boolean broom_allowPlacement(boolean original, @Local(argsOnly = true) World world, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        return original || WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z);
    }

    @ModifyExpressionValue(
            method = "useOnBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;method_1780(III)Z",
                    ordinal = 1
            )
    )
    private boolean broom_allowPlacementOffset(boolean original, @Local(argsOnly = true) PlayerEntity user, @Local(argsOnly = true) World world, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        // Local sugar wasn't capturing var10 and var11 for some reason...
        int var9 = MathHelper.floor((double)(user.yaw * 4.0F / 360.0F) + 0.5) & 3;
        byte var10 = 0;
        byte var11 = 0;
        if (var9 == 0) {
            var11 = 1;
        }

        if (var9 == 1) {
            var10 = -1;
        }

        if (var9 == 2) {
            var11 = -1;
        }

        if (var9 == 3) {
            var10 = 1;
        }
        return original || WorldHelper.isBlockStateFloorSupport(world, x + var10, y - 1, z + var11);
    }
}
