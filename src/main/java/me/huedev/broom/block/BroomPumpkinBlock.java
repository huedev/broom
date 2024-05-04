package me.huedev.broom.block;

import net.minecraft.block.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class BroomPumpkinBlock extends TemplateBlock {
    public BroomPumpkinBlock(Identifier id, Material material) {
        super(id, material);
        setTranslationKey(id);
        setHardness(PUMPKIN.getHardness());
        setSoundGroup(WOOD_SOUND_GROUP);
        ignoreMetaUpdates();
        this.setTickRandomly(true);
    }
}
