package net.huedev.broom.listener;

import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.lwjgl.input.Keyboard;

/**
 * @author DanyGames2014
 */
public class KeyPressedListener {
    Minecraft minecraft = null;

    @EventListener
    public void keyPress(KeyStateChangedEvent event) {
        if (minecraft == null) {
            minecraft = ((Minecraft) FabricLoader.getInstance().getGameInstance());
        }

        if (Keyboard.getEventKeyState() && minecraft.currentScreen == null) {
            // Dismount
            if (Keyboard.isKeyDown(KeyBindingListener.dismount.code)) {
                dismount();
            }
        }
    }

    public void dismount() {
        if (minecraft.player == null) {
            return;
        }

        if (minecraft.player.vehicle == null) {
            return;
        }

        if (!minecraft.world.isRemote) {
            minecraft.player.vehicle.interact(minecraft.player);
        } else {
            PacketHelper.send(new PlayerInteractEntityC2SPacket(minecraft.player.id, minecraft.player.vehicle.id, 0));
        }
    }
}
