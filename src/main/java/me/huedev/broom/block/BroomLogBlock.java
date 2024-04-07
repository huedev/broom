package me.huedev.broom.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author paulevs
 */
public class BroomLogBlock extends TemplateBlock {
    public BroomLogBlock(Identifier identifier) {
        super(identifier, Material.WOOD);
        setTranslationKey(identifier.toString());
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
