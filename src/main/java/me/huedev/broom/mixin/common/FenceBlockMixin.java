package me.huedev.broom.mixin.common;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.Material;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FenceBlock.class)
public class FenceBlockMixin extends Block {
    public FenceBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    private void broom_bypassPlacementRestriction(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canPlaceAt(world, x, y, z));
    }
}
