package me.huedev.broom.listener;

import me.huedev.broom.Broom;
import me.huedev.broom.block.BroomBlocks;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;

@SuppressWarnings("unused")
public class TextureListener {
    @EventListener
    public void registerTexture(TextureRegisterEvent event) {
        ExpandableAtlas terrain = Atlases.getTerrain();

        BroomBlocks.IRON_TRAPDOOR.textureId = terrain.addTexture(Broom.id("block/iron_trapdoor")).index;
    }
}
