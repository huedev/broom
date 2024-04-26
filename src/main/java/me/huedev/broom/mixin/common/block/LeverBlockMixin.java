package me.huedev.broom.mixin.common.block;

import me.huedev.broom.block.BroomBlockProperties;
import me.huedev.broom.block.BroomBlockTags;
import me.huedev.broom.util.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.util.Util;
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
    @Shadow protected abstract boolean method_1785(World arg, int i, int j, int k);

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
        if (side == 0 && world.method_1780(x, y + 1, z)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "canPlaceAt(Lnet/minecraft/world/World;III)Z", at = @At("HEAD"), cancellable = true)
    public void broom_canPlaceAt(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (world.method_1780(x, y + 1, z)) {
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
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();

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

        /*
        int meta = world.getBlockMeta(pos.getX(), pos.getY(), pos.getZ());
        int var7 = meta & 8;
        meta &= 7;
        meta = -1;
        if (side == Direction.UP && world.method_1780(pos.getX(), pos.getY() - 1, pos.getZ())) {
            switch (facing) {
                case EAST, WEST -> meta = 5;
                case NORTH, SOUTH -> meta = 6;
            }
        }

        if (side == Direction.EAST && world.method_1780(pos.getX(), pos.getY(), pos.getZ() + 1)) {
            meta = 4;
        }

        if (side == Direction.WEST && world.method_1780(pos.getX(), pos.getY(), pos.getZ() - 1)) {
            meta = 3;
        }

        if (side == Direction.NORTH && world.method_1780(pos.getX() + 1, pos.getY(), pos.getZ())) {
            meta = 2;
        }

        if (side == Direction.UP && world.method_1780(pos.getX() - 1, pos.getY(), pos.getZ())) {
            meta = 1;
        }

        if (meta == -1) {
            this.dropStacks(world, pos.getX(), pos.getY(), pos.getZ(), world.getBlockMeta(pos.getX(), pos.getY(), pos.getZ()));
            world.setBlock(pos.getX(), pos.getY(), pos.getZ(), 0);
        } else {
            world.method_215(pos.getX(), pos.getY(), pos.getZ(), meta + var7);
        }
        */

        return state;
    }

    @Inject(method = "neighborUpdate", at = @At("HEAD"), cancellable = true)
    private void broom_neighborUpdate(World world, int x, int y, int z, int id, CallbackInfo ci) {
        ci.cancel();

        if (this.method_1785(world, x, y, z)) {
            BlockState state = world.getBlockState(x, y, z);
            BroomBlockProperties.Face face = state.get(BroomBlockProperties.FACE);

            boolean invalid = false;

            if (face == BroomBlockProperties.Face.CEILING && !world.method_1780(x, y + 1, z)) {
                invalid = true;
            } else if (face == BroomBlockProperties.Face.FLOOR && !world.method_1780(x, y - 1, z)) {
                invalid = true;
            } else if (face == BroomBlockProperties.Face.WALL) {
                Direction facing = state.get(BroomBlockProperties.FACING);
                if (!world.method_1780(x + facing.getOffsetX(), y, z + facing.getOffsetZ())) {
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
            world.method_202(x, y, z, x, y, z);
            world.playSound((double)x + 0.5, (double)y + 0.5, (double)z + 0.5, "random.click", 0.3F, powered ? 0.6F : 0.5F);
            world.method_244(x, y, z, this.id);

            BroomBlockProperties.Face face = state.get(BroomBlockProperties.FACE);
            if (face == BroomBlockProperties.Face.CEILING) {
                world.method_244(x, y + 1, z, this.id);
            } else if (face == BroomBlockProperties.Face.FLOOR) {
                world.method_244(x, y - 1, z, this.id);
            } else {
                Direction facing = state.get(BroomBlockProperties.FACING);
                world.method_244(x + facing.getOffsetX(), y, z + facing.getOffsetZ(), this.id);
            }
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, BlockState state, int meta) {
        if (!state.isOf(this)) return;

        boolean powered = state.get(BroomBlockProperties.POWERED);

        if (powered) {
            world.method_244(x, y, z, this.id);
            BroomBlockProperties.Face face = state.get(BroomBlockProperties.FACE);
            if (face == BroomBlockProperties.Face.CEILING) {
                world.method_244(x, y + 1, z, this.id);
            } else if (face == BroomBlockProperties.Face.FLOOR) {
                world.method_244(x, y - 1, z, this.id);
            } else {
                Direction facing = state.get(BroomBlockProperties.FACING);
                world.method_244(x + facing.getOffsetX(), y, z + facing.getOffsetZ(), this.id);
            }
        }
    }

    /*
    @Inject(method = "onBreak", at = @At("HEAD"), cancellable = true)
    private void broom_onBreak(World world, int x, int y, int z, CallbackInfo ci) {
        ci.cancel();

        world.method_244(x, y, z, this.id);

        BlockState state = world.getBlockState(x, y, z);
        if (!state.isOf(this)) return;

        BroomBlockProperties.Face face = state.get(BroomBlockProperties.FACE);
        if (face == BroomBlockProperties.Face.CEILING) {
            world.method_244(x, y + 1, z, this.id);
        } else if (face == BroomBlockProperties.Face.FLOOR) {
            world.method_244(x, y - 1, z, this.id);
        } else {
            Direction facing = state.get(BroomBlockProperties.FACING);
            world.method_244(x + facing.getOffsetX(), y, z + facing.getOffsetZ(), this.id);
        }

        super.onBreak(world, x, y, z);
    }
    */

    @Inject(method = "isEmittingRedstonePower", at = @At("HEAD"), cancellable = true)
    private void broom_isEmittingRedstonePower(BlockView blockView, int x, int y, int z, int meta, CallbackInfoReturnable<Boolean> cir) {
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

    // TODO: canTransferPowerInDirection()
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
