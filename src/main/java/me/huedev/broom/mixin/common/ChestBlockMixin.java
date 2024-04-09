package me.huedev.broom.mixin.common;

import me.huedev.broom.block.BroomBlockProperties;
import me.huedev.broom.block.BroomBlockProperties.ChestPart;
import me.huedev.broom.util.WorldHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BeforeBlockRemoved;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author paulevsGitch
 */
@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin extends BlockWithEntity implements BeforeBlockRemoved {
    protected ChestBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BroomBlockProperties.FACING, BroomBlockProperties.CHEST_PART);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        Direction facing = Direction.fromRotation(player == null ? 0 : player.yaw);

        BlockState chest = getDefaultState().with(BroomBlockProperties.FACING, facing);
        if (player != null && player.method_1373()) {
            return chest.with(BroomBlockProperties.CHEST_PART, ChestPart.SINGLE);
        }

        Direction side = facing.rotateCounterclockwise(Direction.Axis.Y);
        BlockPos sidePos = pos.offset(side);
        BlockState sideState = world.getBlockState(sidePos);
        if (
                sideState.isOf(this) &&
                        sideState.get(BroomBlockProperties.CHEST_PART) == ChestPart.SINGLE &&
                        sideState.get(BroomBlockProperties.FACING) == facing
        ) {
            sideState = sideState.with(BroomBlockProperties.CHEST_PART, ChestPart.LEFT);
            WorldHelper.setBlockSilent(world, sidePos.getX(), sidePos.getY(), sidePos.getZ(), sideState);
            return chest.with(BroomBlockProperties.CHEST_PART, ChestPart.RIGHT);
        }

        side = facing.rotateClockwise(Direction.Axis.Y);
        sidePos = pos.offset(side);
        sideState = world.getBlockState(sidePos);
        if (
                sideState.isOf(this) &&
                        sideState.get(BroomBlockProperties.CHEST_PART) == ChestPart.SINGLE &&
                        sideState.get(BroomBlockProperties.FACING) == facing
        ) {
            sideState = sideState.with(BroomBlockProperties.CHEST_PART, ChestPart.RIGHT);
            WorldHelper.setBlockSilent(world, sidePos.getX(), sidePos.getY(), sidePos.getZ(), sideState);
            return chest.with(BroomBlockProperties.CHEST_PART, ChestPart.LEFT);
        }

        return chest.with(BroomBlockProperties.CHEST_PART, ChestPart.SINGLE);
    }

    @Override
    public void beforeBlockRemoved(World world, int x, int y, int z) {
        BlockState state = world.getBlockState(x, y, z);
        if (!state.isOf(this)) return;
        ChestPart part = state.get(BroomBlockProperties.CHEST_PART);
        if (part == ChestPart.SINGLE) return;
        Direction facing = state.get(BroomBlockProperties.FACING);
        Direction side = part == ChestPart.RIGHT ? facing.rotateCounterclockwise(Direction.Axis.Y) : facing.rotateClockwise(Direction.Axis.Y);
        x += side.getOffsetX();
        y += side.getOffsetY();
        z += side.getOffsetZ();
        state = world.getBlockState(x, y, z);
        if (state.isOf(this)) {
            WorldHelper.setBlockSilent(world, x, y, z, state.with(BroomBlockProperties.CHEST_PART, ChestPart.SINGLE));
        }
    }

    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    private void broom_canPlaceAt(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(true);
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void broom_onUse(World world, int x, int y, int z, PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(true);

        if (world.isRemote) {
            return;
        }

        Inventory inventory = (Inventory) world.method_1777(x, y, z);
        BlockState state = world.getBlockState(x, y, z);

        ChestPart part = state.get(BroomBlockProperties.CHEST_PART);
        if (part == ChestPart.SINGLE) {
            player.method_486(inventory);
            return;
        }

        Direction facing = state.get(BroomBlockProperties.FACING);
        Direction side = part == ChestPart.RIGHT ? facing.rotateCounterclockwise(Direction.Axis.Y) : facing.rotateClockwise(Direction.Axis.Y);
        x += side.getOffsetX();
        y += side.getOffsetY();
        z += side.getOffsetZ();

        state = world.getBlockState(x, y, z);
        if (!state.isOf(this)) {
            player.method_486(inventory);
            return;
        }

        Inventory sideInventory = (Inventory) world.method_1777(x, y, z);

        switch (part) {
            case LEFT -> inventory = new DoubleInventory("Large Chest", inventory, sideInventory);
            case RIGHT -> inventory = new DoubleInventory("Large Chest", sideInventory, inventory);
        }

        player.method_486(inventory);
    }

    @Environment(value = EnvType.CLIENT)
    @Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
    private void broom_getTextureForSide(BlockView view, int x, int y, int z, int side, CallbackInfoReturnable<Integer> info) {
        if (side < 2) return;
        if (!(view instanceof BlockStateView blockStateView)) return;
        BlockState state = blockStateView.getBlockState(x, y, z);
        Direction facing = state.get(BroomBlockProperties.FACING);
        if (facing.getId() != side && facing.getOpposite().getId() != side) {
            info.setReturnValue(this.textureId);
            return;
        }
        ChestPart part = state.get(BroomBlockProperties.CHEST_PART);
        int offset = facing.getId() == side ? 0 : 1;
        switch (part) {
            case RIGHT -> offset = facing.getId() == side ? 31 : 16;
            case LEFT -> offset = facing.getId() == side ? 32 : 15;
        }
        info.setReturnValue(this.textureId + offset);
    }
}