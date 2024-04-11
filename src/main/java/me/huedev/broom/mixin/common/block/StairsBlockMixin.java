package me.huedev.broom.mixin.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DanyGames2014
 */
@Mixin(StairsBlock.class)
public class StairsBlockMixin extends Block {
    public StairsBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Inject(method = "dropStacks", at = @At("HEAD"), cancellable = true)
    public void broom_dropStacks(World world, int x, int y, int z, int meta, float f, CallbackInfo ci) {
        int droppedItemCount = this.getDroppedItemCount(world.field_214);

        for (int i = 0; i < droppedItemCount; ++i) {
            this.dropStack(world, x, y, z, new ItemStack(id, 1, this.getDroppedItemMeta(meta)));
        }
        ci.cancel();
    }
}
