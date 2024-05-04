package me.huedev.broom.listener;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.option.KeyBinding;
import net.modificationstation.stationapi.api.client.event.option.KeyBindingRegisterEvent;
import org.lwjgl.input.Keyboard;

/**
 * @author DanyGames2014
 */
public class KeyBindingListener {
    public static KeyBinding dismount;

    @EventListener
    public void registerKeyBindings(KeyBindingRegisterEvent event) {
        event.keyBindings.add(dismount = new KeyBinding("key.broom.dismount", Keyboard.KEY_LSHIFT));
    }
}
