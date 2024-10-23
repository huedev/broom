package net.huedev.broom.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class BroomGlowstoneLampBlock extends TemplateBlock {
    public BroomGlowstoneLampBlock(Identifier id) {
        super(id, Material.GLASS);
        setTranslationKey(id);
        setHardness(0.3F);
        setSoundGroup(GLASS_SOUND_GROUP);
        setDefaultState(getDefaultState().with(BroomBlockProperties.LIT, false));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(
                BroomBlockProperties.LIT
        );
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = getDefaultState();

        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        boolean lit = world.isEmittingRedstonePower(pos.getX(), pos.getY(), pos.getZ());
        state = state.with(BroomBlockProperties.LIT, lit);
        this.setLuminance(lit ? 1.0F : 0.0F);

        return state;
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int blockID) {
        if (!world.isRemote) {
            BlockState state = world.getBlockState(x, y, z);
            if (!state.isOf(this)) return;

            if (blockID > 0 && Block.BLOCKS[blockID].canEmitRedstonePower()) {
                boolean lit = world.isEmittingRedstonePower(x, y, z);
                this.setLuminance(lit ? 1.0F : 0.0F);
                if (lit != state.get(BroomBlockProperties.LIT)) {
                    state = state.with(BroomBlockProperties.LIT, lit);
                    world.setBlockStateWithNotify(x, y, z, state);
                    world.setBlockDirty(x, y, z);
                }
            }
        }
    }
}
