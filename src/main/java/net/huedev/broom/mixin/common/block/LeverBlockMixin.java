package net.huedev.broom.mixin.common.block;

import net.huedev.broom.block.BroomBlockProperties;
import net.huedev.broom.util.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeverBlock.class)
public abstract class LeverBlockMixin extends Block {
    @Shadow protected abstract boolean breakIfCannotPlaceAt(World arg, int i, int j, int k);

    public LeverBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(
                BroomBlockProperties.FACE,
                BroomBlockProperties.FACING,
                BroomBlockProperties.POWERED
        );
    }

    @Inject(method = "canPlaceAt(Lnet/minecraft/world/World;IIII)Z", at = @At("HEAD"), cancellable = true)
    public void broom_canPlaceAt(World world, int x, int y, int z, int side, CallbackInfoReturnable<Boolean> cir) {
        if (side == 0 && world.shouldSuffocate(x, y + 1, z)) {
            cir.setReturnValue(true);
        }

        if (WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "canPlaceAt(Lnet/minecraft/world/World;III)Z", at = @At("HEAD"), cancellable = true)
    public void broom_canPlaceAt(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (world.shouldSuffocate(x, y + 1, z)) {
            cir.setReturnValue(true);
        }

        if (WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onBlockBreakStart", at = @At("HEAD"), cancellable = true)
    private void broom_cancelInteract(World world, int x, int y, int z, PlayerEntity player, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onPlaced", at = @At("HEAD"), cancellable = true)
    private void broom_cancelOnPlaced(World world, int x, int y, int z, int direction, CallbackInfo ci) {
        ci.cancel();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction side = context.getSide();
        Direction facing = context.getSide().getOpposite();
        PlayerEntity player = context.getPlayer();

        if (facing.getAxis() == Direction.Axis.Y) {
            facing = Direction.fromRotation(player == null ? 0 : player.yaw);
        }

        BlockState state = getDefaultState().with(BroomBlockProperties.FACING, facing);
        state = state.with(BroomBlockProperties.POWERED, false);

        if (player != null) {
            if (side.equals(Direction.DOWN)) {
                state = state.with(BroomBlockProperties.FACE, BroomBlockProperties.Face.CEILING);
            } else if (side.equals(Direction.UP)) {
                state = state.with(BroomBlockProperties.FACE, BroomBlockProperties.Face.FLOOR);
            } else {
                state = state.with(BroomBlockProperties.FACE, BroomBlockProperties.Face.WALL);
            }
        }

        return state;
    }

    @Inject(method = "neighborUpdate", at = @At("HEAD"), cancellable = true)
    private void broom_neighborUpdate(World world, int x, int y, int z, int id, CallbackInfo ci) {
        ci.cancel();

        if (this.breakIfCannotPlaceAt(world, x, y, z)) {
            BlockState state = world.getBlockState(x, y, z);
            BroomBlockProperties.Face face = state.get(BroomBlockProperties.FACE);

            boolean invalid = false;

            if (face == BroomBlockProperties.Face.CEILING && !world.shouldSuffocate(x, y + 1, z)) {
                invalid = true;
            } else if (face == BroomBlockProperties.Face.FLOOR) {
                if (!world.shouldSuffocate(x, y - 1, z) && !WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z)) {
                    invalid = true;
                }
            } else if (face == BroomBlockProperties.Face.WALL) {
                Direction facing = state.get(BroomBlockProperties.FACING);
                if (!world.shouldSuffocate(x + facing.getOffsetX(), y, z + facing.getOffsetZ())) {
                    invalid = true;
                }
            }

            if (invalid) {
                this.dropStacks(world, x, y, z, world.getBlockMeta(x, y, z));
                world.setBlock(x, y, z, 0);
            }
        }
    }

    @Inject(method = "updateBoundingBox", at = @At("HEAD"), cancellable = true)
    private void broom_updateBoundingBox(BlockView blockView, int x, int y, int z, CallbackInfo ci) {
        ci.cancel();

        if (!(blockView instanceof BlockStateView world)) return;
        BlockState state = world.getBlockState(x, y, z);
        if (!state.isOf(this)) return;

        float var6 = 0.1875F;
        BroomBlockProperties.Face face = state.get(BroomBlockProperties.FACE);
        if (face == BroomBlockProperties.Face.CEILING) {
            var6 = 0.25F;
            this.setBoundingBox(0.5F - var6, 0.4F, 0.5F - var6, 0.5F + var6, 1.0F, 0.5F + var6);
        } else if (face == BroomBlockProperties.Face.FLOOR) {
            var6 = 0.25F;
            this.setBoundingBox(0.5F - var6, 0.0F, 0.5F - var6, 0.5F + var6, 0.6F, 0.5F + var6);
        } else {
            Direction facing = state.get(BroomBlockProperties.FACING);
            switch (facing) {
                case NORTH -> this.setBoundingBox(0.0F, 0.2F, 0.5F - var6, var6 * 2.0F, 0.8F, 0.5F + var6);
                case SOUTH -> this.setBoundingBox(1.0F - var6 * 2.0F, 0.2F, 0.5F - var6, 1.0F, 0.8F, 0.5F + var6);
                case EAST -> this.setBoundingBox(0.5F - var6, 0.2F, 0.0F, 0.5F + var6, 0.8F, var6 * 2.0F);
                case WEST -> this.setBoundingBox(0.5F - var6, 0.2F, 1.0F - var6 * 2.0F, 0.5F + var6, 0.8F, 1.0F);
            }
        }
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void broom_onUse(World world, int x, int y, int z, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);

        if (!world.isRemote) {
            BlockState state = world.getBlockState(x, y, z);
            if (!state.isOf(this)) return;

            boolean powered = !state.get(BroomBlockProperties.POWERED);
            BlockState changed = state.with(BroomBlockProperties.POWERED, powered);

            if (changed == state) return;

            world.setBlockStateWithNotify(x, y, z, changed);
            world.setBlocksDirty(x, y, z, x, y, z);
            if (powered) {
                world.worldEvent(null, 1009, x, y, z, 0);
            } else {
                world.worldEvent(null, 1010, x, y, z, 0);
            }
            world.notifyNeighbors(x, y, z, this.id);

            BroomBlockProperties.Face face = state.get(BroomBlockProperties.FACE);
            if (face == BroomBlockProperties.Face.CEILING) {
                world.notifyNeighbors(x, y + 1, z, this.id);
            } else if (face == BroomBlockProperties.Face.FLOOR) {
                world.notifyNeighbors(x, y - 1, z, this.id);
            } else {
                Direction facing = state.get(BroomBlockProperties.FACING);
                world.notifyNeighbors(x + facing.getOffsetX(), y, z + facing.getOffsetZ(), this.id);
            }
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, BlockState state, int meta) {
        super.afterBreak(world, player, x, y, z, state, meta);

        if (!state.isOf(this)) return;

        boolean powered = state.get(BroomBlockProperties.POWERED);

        if (powered) {
            world.notifyNeighbors(x, y, z, this.id);
            BroomBlockProperties.Face face = state.get(BroomBlockProperties.FACE);
            if (face == BroomBlockProperties.Face.CEILING) {
                world.notifyNeighbors(x, y + 1, z, this.id);
            } else if (face == BroomBlockProperties.Face.FLOOR) {
                world.notifyNeighbors(x, y - 1, z, this.id);
            } else {
                Direction facing = state.get(BroomBlockProperties.FACING);
                world.notifyNeighbors(x + facing.getOffsetX(), y, z + facing.getOffsetZ(), this.id);
            }
        }
    }

    @Inject(method = "isEmittingRedstonePowerInDirection", at = @At("HEAD"), cancellable = true)
    private void broom_isEmittingRedstonePowerInDirection(BlockView blockView, int x, int y, int z, int meta, CallbackInfoReturnable<Boolean> cir) {
        if (!(blockView instanceof BlockStateView world)) {
            cir.setReturnValue(false);
            return;
        }
        BlockState state = world.getBlockState(x, y, z);
        if (!state.isOf(this)) {
            cir.setReturnValue(false);
            return;
        }

        cir.setReturnValue(state.get(BroomBlockProperties.POWERED));
    }

    @Inject(method = "canTransferPowerInDirection", at = @At("HEAD"), cancellable = true)
    private void broom_canTransferPowerInDirection(World world, int x, int y, int z, int direction, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = world.getBlockState(x, y, z);
        if (!state.isOf(this)) {
            cir.setReturnValue(false);
            return;
        }

        boolean powered = state.get(BroomBlockProperties.POWERED);
        if (!powered) {
            cir.setReturnValue(false);
            return;
        }

        BroomBlockProperties.Face face = state.get(BroomBlockProperties.FACE);
        if (face == BroomBlockProperties.Face.CEILING) {
            cir.setReturnValue(direction == 0);
        } else if (face == BroomBlockProperties.Face.FLOOR) {
            cir.setReturnValue(direction == 1);
        } else {
            Direction facing = state.get(BroomBlockProperties.FACING);
            if (facing == Direction.WEST && direction == 2) {
                cir.setReturnValue(true);
            } else if (facing == Direction.EAST && direction == 3) {
                cir.setReturnValue(true);
            } else if (facing == Direction.SOUTH && direction == 4) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(facing == Direction.NORTH && direction == 5);
            }
        }
    }
}
