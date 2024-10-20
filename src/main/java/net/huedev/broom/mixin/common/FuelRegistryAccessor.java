package net.huedev.broom.mixin.common;

import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.recipe.FuelRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FuelRegistry.class)
public interface FuelRegistryAccessor {
    @Accessor("ITEM_FUEL_TIME")
    static Reference2IntMap<Item> broom_getFuelItems() {
        throw new AssertionError();
    }

    @Accessor("TAG_FUEL_TIME")
    static Reference2IntMap<TagKey<Item>> broom_getFuelTags() {
        throw new AssertionError();
    }
}
