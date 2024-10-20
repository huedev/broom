package net.huedev.broom.mixin.common.block.entity;

import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = FurnaceBlockEntity.class, priority = 1100)
public class FurnaceBlockEntityMixin {
    @Redirect(
            method = "craftRecipe",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.PUTFIELD,
                    target = "Lnet/minecraft/item/ItemStack;count:I",
                    ordinal = 1
            ),
            require = 0
    )
    public void broom_preserveInputBucket(ItemStack stack, int count) {
        if (stack.itemId == Item.MILK_BUCKET.id) {
            stack.itemId = Item.BUCKET.id;
            stack.count = 1;
        } else {
            stack.count = count;
        }
    }

    @Redirect(
            method = "tick",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.PUTFIELD,
                    target = "Lnet/minecraft/item/ItemStack;count:I"
            ),
            require = 0
    )
    public void broom_preserveFuelBucket(ItemStack stack, int count) {
        if (stack.itemId == Item.LAVA_BUCKET.id) {
            stack.itemId = Item.BUCKET.id;
            stack.count = 1;
        } else {
            stack.count = count;
        }
    }
}
