package net.huedev.broom.mixin.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author DanyGames2014
 */
@Mixin(ArmorItem.class)
public class ArmorItemMixin extends Item {
    @Shadow
    @Final
    public int equipmentSlot;

    public ArmorItemMixin(int id) {
        super(id);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity player) {
        if (player.inventory.armor[Math.abs(this.equipmentSlot - 3)] == null) {
            player.inventory.armor[Math.abs(this.equipmentSlot - 3)] = stack.copy();
            stack.count = 0;
        } else {
            ItemStack temp = player.inventory.armor[Math.abs(this.equipmentSlot - 3)];
            player.inventory.armor[Math.abs(this.equipmentSlot - 3)] = stack.copy();
            return temp;
        }
        return stack;
    }
}
