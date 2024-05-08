package me.huedev.broom.listener;

import me.huedev.broom.Broom;
import me.huedev.broom.block.BroomBlocks;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import net.modificationstation.stationapi.api.util.Namespace;

@SuppressWarnings("unused")
public class TextureListener {
    @EventListener
    public void registerTexture(TextureRegisterEvent event) {
        ExpandableAtlas terrain = Atlases.getTerrain();

        BroomBlocks.CACAO_SAPLING.textureId = terrain.addTexture(Broom.id("block/cacao_sapling")).index;
        BroomBlocks.POLISHED_STONE.textureId = terrain.addTexture(Broom.id("block/polished_stone")).index;
        BroomBlocks.POLISHED_STONE_BRICKS.textureId = terrain.addTexture(Broom.id("block/polished_stone_bricks")).index;
        BroomBlocks.SANDSTONE_BRICKS.textureId = terrain.addTexture(Broom.id("block/sandstone_bricks")).index;
        BroomBlocks.SNOW_BRICKS.textureId = terrain.addTexture(Broom.id("block/snow_bricks")).index;
        BroomBlocks.IRON_TRAPDOOR.textureId = terrain.addTexture(Broom.id("block/iron_trapdoor")).index;
    }
}
