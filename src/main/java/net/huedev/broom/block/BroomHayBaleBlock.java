package net.huedev.broom.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

public class BroomHayBaleBlock extends TemplateBlock {
    public BroomHayBaleBlock(Identifier id) {
        super(id, Material.SOLID_ORGANIC);
        setTranslationKey(id);
        setHardness(0.5F);
        setSoundGroup(DIRT_SOUND_GROUP);
        setDefaultState(getDefaultState().with(BroomBlockProperties.AXIS, Direction.Axis.Y));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BroomBlockProperties.AXIS);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction.Axis axis = context.getSide().getAxis();
        return getDefaultState().with(BroomBlockProperties.AXIS, axis);
    }
}
