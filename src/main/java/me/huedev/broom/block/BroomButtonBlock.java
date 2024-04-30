package me.huedev.broom.block;

import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.template.block.TemplateButtonBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class BroomButtonBlock extends TemplateButtonBlock {
    private final int tickRate;

    public BroomButtonBlock(Identifier id, Block baseBlock, int tickRate) {
        super(id, baseBlock.textureId);
        this.tickRate = tickRate;
        setTranslationKey(id.toString());
        setSoundGroup(baseBlock.soundGroup);
        setHardness(0.5F);
        ignoreMetaUpdates();
    }

    @Override
    public int getTickRate() {
        return tickRate;
    }
}
