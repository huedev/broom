package me.huedev.broom.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author DanyGames2014
 */
@Mixin(BowItem.class)
public class BowItemMixin extends Item {
    public BowItemMixin(int id) {
        super(id);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isHandheld() {
        return true;
    }
}
