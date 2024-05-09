package me.huedev.broom.block;

import me.huedev.broom.util.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.world.BlockStateView;

public class BroomFenceGateBlock extends TemplateBlock {
    public BroomFenceGateBlock(Identifier id) {
        this(id, Material.WOOD);
    }

    public BroomFenceGateBlock(Identifier id, Material material) {
        super(id, Material.WOOD);
        setTranslationKey(id);
        setHardness(FENCE.getHardness());
        setSoundGroup(WOOD_SOUND_GROUP);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(
                BroomBlockProperties.FACING,
                BroomBlockProperties.OPENED
        );
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction facing = context.getSide().getOpposite();
        PlayerEntity player = context.getPlayer();

        if (facing.getAxis() == Direction.Axis.Y) {
            facing = Direction.fromRotation(player == null ? 0 : player.yaw);
        }

        BlockState state = getDefaultState().with(BroomBlockProperties.FACING, facing);

        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        boolean opened = world.method_265(pos.getX(), pos.getY(), pos.getZ());
        state = state.with(BroomBlockProperties.OPENED, opened);

        return state;
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int blockID) {
        if (!world.isRemote) {
            BlockState state = world.getBlockState(x, y, z);
            if (!state.isOf(this)) return;

            if (blockID > 0 && Block.BLOCKS[blockID].canEmitRedstonePower()) {
                boolean opened = world.method_265(x, y, z);
                if (opened != state.get(BroomBlockProperties.OPENED)) {
                    state = state.with(BroomBlockProperties.OPENED, opened);
                    world.setBlockStateWithNotify(x, y, z, state);
                    world.method_246(x, y, z);
                    if (opened) {
                        world.method_173(null, 1006, x, y, z, 0);
                    } else {
                        world.method_173(null, 1007, x, y, z, 0);
                    }
                }
            }
        }
    }

    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        BlockState state = world.getBlockState(x, y, z);
        if (state.isIn(BroomBlockTags.REQUIRES_POWER)) return false;
        if (!state.isOf(this)) return false;

        Direction facing = state.get(BroomBlockProperties.FACING);

        boolean opened = !state.get(BroomBlockProperties.OPENED) || world.method_265(x, y, z);
        BlockState changed = state.with(BroomBlockProperties.OPENED, opened);

        if (changed == state) return false;

        HitResult hit = WorldHelper.raycast(world, player);
        Direction hitDirection = Direction.byId(hit.side);
        if (hitDirection.getHorizontal() != -1) {
            if (hitDirection.getAxis() == facing.getAxis()) {
                changed = changed.with(BroomBlockProperties.FACING, hitDirection.getOpposite());
            }
        } else {
            Direction directionFromPlayerLookAngle = Direction.fromHorizontal(MathHelper.floor((double)(player.yaw * 4.0F / 360.0F) + 2.5) & 3);
            if (directionFromPlayerLookAngle.getAxis() == facing.getAxis()) {
                changed = changed.with(BroomBlockProperties.FACING, directionFromPlayerLookAngle.getOpposite());
            }
        }

        world.setBlockStateWithNotify(x, y, z, changed);
        world.method_246(x, y, z);
        if (!world.isRemote) {
            if (opened) {
                world.method_173(null, 1006, x, y, z, 0);
            } else {
                world.method_173(null, 1007, x, y, z, 0);
            }
        }
        return true;
    }

    @Override
    public Box getCollisionShape(World world, int x, int y, int z) {
        BlockState state = world.getBlockState(x, y, z);
        if (!state.isOf(this)) return null;

        boolean opened = state.get(BroomBlockProperties.OPENED);

        Direction d = state.get(BroomBlockProperties.FACING);

        if (!opened) {
            switch (d.getAxis()) {
                case X -> {
                    return Box.create((double)((float)x + 0.375F), (double)y, (double)z, (double)((float)x + 0.625F), (double)((float)y + 1.5F), (double)((float)z + 1F));
                }
                case Z -> {
                    return Box.create((double)x, (double)y, (double)((float)z + 0.375F), (double)((float)x + 1F), (double)((float)y + 1.5F), (double)((float)z + 0.625F));
                }
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public Box getBoundingBox(World world, int x, int y, int z) {
        BlockState state = world.getBlockState(x, y, z);
        if (!state.isOf(this)) return null;

        Direction d = state.get(BroomBlockProperties.FACING);
        switch (d.getAxis()) {
            case X -> {
                return Box.create((double)((float)x + 0.375F), (double)y, (double)z, (double)((float)x + 0.625F), (double)((float)y + 1F), (double)((float)z + 1F));
            }
            case Z -> {
                return Box.create((double)x, (double)y, (double)((float)z + 0.375F), (double)((float)x + 1), (double)((float)y + 1F), (double)((float)z + 0.625F));
            }
        }
        return null;
    }

    @Override
    public void updateBoundingBox(BlockView view, int x, int y, int z) {
        if (!(view instanceof BlockStateView bsView)) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            return;
        }

        BlockState state = bsView.getBlockState(x, y, z);
        if (!state.isOf(this)) return;
        Direction facing = state.get(BroomBlockProperties.FACING);

        if (facing.getAxis() == Direction.Axis.X) {
            this.setBoundingBox(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
        } else {
            this.setBoundingBox(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
        }
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }
}
