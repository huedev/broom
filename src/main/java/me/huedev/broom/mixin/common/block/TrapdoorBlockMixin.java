package me.huedev.broom.mixin.common.block;

import me.huedev.broom.block.BroomBlockProperties;
import me.huedev.broom.block.BroomBlockProperties.TopBottom;
import me.huedev.broom.block.BroomBlockTags;
import me.huedev.broom.util.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author paulevsGitch
 */
@Mixin(TrapdoorBlock.class)
public class TrapdoorBlockMixin extends Block {
    public TrapdoorBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Override
    public void appendProperties(Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(
                BroomBlockProperties.FACING,
                BroomBlockProperties.TOP_BOTTOM,
                BroomBlockProperties.OPENED
        );
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction side = context.getSide();
        Direction facing = context.getSide().getOpposite();
        PlayerEntity player = context.getPlayer();

        if (facing.getAxis() == Axis.Y) {
            facing = Direction.fromRotation(player == null ? 0 : player.yaw);
        }

        BlockState state = getDefaultState().with(BroomBlockProperties.FACING, facing);

        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        boolean opened = world.method_265(pos.getX(), pos.getY(), pos.getZ());
        state = state.with(BroomBlockProperties.OPENED, opened);

        if (player != null) {
            if (side.equals(Direction.DOWN)) {
                state = state.with(BroomBlockProperties.TOP_BOTTOM, TopBottom.TOP);
            } else if (side.equals(Direction.UP)) {
                state = state.with(BroomBlockProperties.TOP_BOTTOM, TopBottom.BOTTOM);
            } else {
                HitResult hit = WorldHelper.raycast(world, player);
                float dy = (float) (hit.pos.y - hit.blockY);
                state = state.with(BroomBlockProperties.TOP_BOTTOM, dy > 0.5F ? TopBottom.TOP : TopBottom.BOTTOM);
            }
        }

        return state;
    }

    @Inject(method = "neighborUpdate", at = @At("HEAD"), cancellable = true)
    private void broom_neighborUpdate(World world, int x, int y, int z, int blockID, CallbackInfo ci) {
        ci.cancel();

        BlockState state = world.getBlockState(x, y, z);
        if (!state.isOf(this)) return;

        if (Block.BLOCKS[blockID].canEmitRedstonePower()) {
            boolean opened = world.method_265(x, y, z);
            if (opened != state.get(BroomBlockProperties.OPENED)) {
                state = state.with(BroomBlockProperties.OPENED, opened);
                WorldHelper.setBlockSilent(world, x, y, z, state);
                world.method_246(x, y, z);
                world.method_173(null, 1003, x, y, z, 0);
            }
        }
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void broom_onUse(World world, int x, int y, int z, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);

        BlockState state = world.getBlockState(x, y, z);
        if (state.isIn(BroomBlockTags.REQUIRES_POWER)) return;
        if (!state.isOf(this)) return;

        boolean opened = !state.get(BroomBlockProperties.OPENED) || world.method_265(x, y, z);
        BlockState changed = state.with(BroomBlockProperties.OPENED, opened);

        if (changed == state) return;

        WorldHelper.setBlockSilent(world, x, y, z, changed);
        world.method_246(x, y, z);
        world.method_173(null, 1003, x, y, z, 0);
    }

    @Inject(method = "updateBoundingBox", at = @At("HEAD"), cancellable = true)
    public void vbe_updateBoundingBox(BlockView view, int x, int y, int z, CallbackInfo info) {
        info.cancel();

        if (!(view instanceof BlockStateView level)) return;
        BlockState state = level.getBlockState(x, y, z);
        if (!state.isOf(this)) return;

        TopBottom part = state.get(BroomBlockProperties.TOP_BOTTOM);
        float min = 3F / 16F;
        float max = 1F - min;

        if (!state.get(BroomBlockProperties.OPENED)) {
            float y1 = part == TopBottom.BOTTOM ? 0.0F : max;
            float y2 = part == TopBottom.BOTTOM ? min : 1.0F;
            this.setBoundingBox(0, y1, 0, 1, y2, 1);
            return;
        }

        Direction d = state.get(BroomBlockProperties.FACING);

        switch (d.getAxis()) {
            case X -> this.setBoundingBox(d.getOffsetX() < 0 ? 0 : max, 0.0F, 0.0F, d.getOffsetX() < 0 ? min : 1, 1.0F, 1.0F);
            case Z -> this.setBoundingBox(0.0F, 0.0F, d.getOffsetZ() < 0 ? 0 : max, 1.0F, 1.0F, d.getOffsetZ() < 0 ? min : 1);
        }
    }

    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    private void vbe_canPlaceAt(World world, int x, int y, int z, int side, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}