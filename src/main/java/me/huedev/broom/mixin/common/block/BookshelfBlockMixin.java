package me.huedev.broom.mixin.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BookshelfBlock;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BookshelfBlock.class)
public class BookshelfBlockMixin extends Block {
    public BookshelfBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Inject(method = "getDroppedItemCount", at = @At("HEAD"), cancellable = true)
    public void broom_changeDroppedItemCount(Random random, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(3);
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return Item.BOOK.id;
    }
}
