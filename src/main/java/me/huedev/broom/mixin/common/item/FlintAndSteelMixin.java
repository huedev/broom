package me.huedev.broom.mixin.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author DanyGames2014
 */
@Mixin(FlintAndSteel.class)
public class FlintAndSteelMixin {
    @Inject(
            method = "useOnBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlock(IIII)Z",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    public void broom_damageOnSuccessfulIgnite(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side, CallbackInfoReturnable<Boolean> cir) {
        stack.damage(1, player);
        cir.setReturnValue(true);
    }

    @ModifyConstant(method = "useOnBlock", constant = @Constant(intValue = 1, ordinal = 1))
    public int broom_disableVanillaDamage(int constant) {
        return 0;
    }
}
