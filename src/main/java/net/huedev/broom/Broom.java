package net.huedev.broom;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.glasslauncher.mods.gcapi3.api.ValueOnVanillaServer;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class Broom {
    public static final Namespace NAMESPACE = Namespace.of("broom");

    @Entrypoint.Logger
    private static final Logger LOGGER = Null.get();

    @ConfigRoot(value = "config", visibleName = "Broom", index = 0)
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {
        @ConfigEntry(name = "Shift-Click from Containers Behavior")
        @ValueOnVanillaServer(integerValue = 0)
        public Boolean shiftClickOutOfContainersBehavior = false;
    }

    public static Identifier id(String name) {
        return NAMESPACE.id(name);
    }
}
