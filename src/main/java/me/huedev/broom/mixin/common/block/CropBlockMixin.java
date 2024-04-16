package me.huedev.broom.mixin.common.block;

import net.minecraft.block.CropBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CropBlock.class)
public class CropBlockMixin extends PlantBlock {
    public CropBlockMixin(int id, int textureId) {
        super(id, textureId);
    }

    @Inject(
            method = "dropStacks",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/PlantBlock;dropStacks(Lnet/minecraft/world/World;IIIIF)V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void broom_cancelSeedDrops(World world, int x, int y, int z, int meta, float f, CallbackInfo ci) {
        if (meta < 7) {
            ci.cancel();
        }
    }

    @ModifyConstant(method = "getDroppedItemId", constant = @Constant(intValue = -1))
    private int broom_dropSeeds(int constant) {
        return Item.SEEDS.id;
    }
}
