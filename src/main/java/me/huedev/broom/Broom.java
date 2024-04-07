package me.huedev.broom;

import net.glasslauncher.mods.api.gcapi.api.ConfigName;
import net.glasslauncher.mods.api.gcapi.api.GConfig;
import net.glasslauncher.mods.api.gcapi.api.MultiplayerSynced;
import net.glasslauncher.mods.api.gcapi.api.ValueOnVanillaServer;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class Broom {
    public static final Namespace NAMESPACE = Namespace.of("broom");

    @Entrypoint.Logger
    private static final Logger LOGGER = Null.get();

    @GConfig(value = "config", visibleName = "Broom")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {
    }

    public static Identifier id(String name) {
        return NAMESPACE.id(name);
    }
}
