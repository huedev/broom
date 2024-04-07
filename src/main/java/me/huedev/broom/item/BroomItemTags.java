package me.huedev.broom.item;

import me.huedev.broom.Broom;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

public class BroomItemTags {
    public static final TagKey<Item> LEAVES = getDefault("leaves");
    public static final TagKey<Item> LOGS = getDefault("logs");

    private static TagKey<Item> get(String name) {
        return TagKey.of(ItemRegistry.KEY, Broom.id(name));
    }

    private static TagKey<Item> getDefault(String name) {
        return TagKey.of(ItemRegistry.KEY, Identifier.of(name));
    }
}
