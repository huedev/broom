package net.huedev.broom.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;

/**
 * @author paulevsGitch
 */
public class BroomLogBlock extends TemplateBlock {
    public BroomLogBlock(Identifier id) {
        super(id, Material.WOOD);
        setTranslationKey(id);
        setHardness(LOG.getHardness());
        setSoundGroup(WOOD_SOUND_GROUP);
        setDefaultState(getDefaultState().with(BroomBlockProperties.AXIS, Axis.Y));
    }

    @Override
    public void appendProperties(Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BroomBlockProperties.AXIS);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Axis axis = context.getSide().getAxis();
        return getDefaultState().with(BroomBlockProperties.AXIS, axis);
    }
}
