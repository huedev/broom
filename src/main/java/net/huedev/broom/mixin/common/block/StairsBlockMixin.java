package net.huedev.broom.mixin.common.block;

import net.huedev.broom.block.BroomBlockProperties;
import net.huedev.broom.block.BroomBlockProperties.TopBottom;
import net.huedev.broom.util.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(StairsBlock.class)
public class StairsBlockMixin extends Block {
    public StairsBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BroomBlockProperties.FACING, BroomBlockProperties.TOP_BOTTOM);
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

        World world = context.getWorld();

        if (player != null) {
            if (side.equals(Direction.DOWN)) {
                state = state.with(BroomBlockProperties.TOP_BOTTOM, BroomBlockProperties.TopBottom.TOP);
            } else if (side.equals(Direction.UP)) {
                state = state.with(BroomBlockProperties.TOP_BOTTOM, BroomBlockProperties.TopBottom.BOTTOM);
            } else {
                HitResult hit = WorldHelper.raycast(world, player);
                float dy = (float) (hit.pos.y - hit.blockY);
                state = state.with(BroomBlockProperties.TOP_BOTTOM, dy > 0.5F ? BroomBlockProperties.TopBottom.TOP : BroomBlockProperties.TopBottom.BOTTOM);
            }
        }

        return state;
    }

    @Inject(method = "onPlaced(Lnet/minecraft/world/World;IIILnet/minecraft/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
    private void broom_onPlaced(World world, int x, int y, int z, LivingEntity placer, CallbackInfo ci) {
        ci.cancel();
    }

    @SuppressWarnings("rawtypes")
    @Inject(method = "addIntersectingBoundingBox", at = @At("HEAD"), cancellable = true)
    public void broom_addIntersectingBoundingBox(World world, int x, int y, int z, Box box, ArrayList boxes, CallbackInfo ci) {
        BlockState state = world.getBlockState(x, y, z);
        Direction facing = state.get(BroomBlockProperties.FACING);
        TopBottom type = state.get(BroomBlockProperties.TOP_BOTTOM);

        switch (type) {
            case BOTTOM -> {
                switch (facing) {
                    case WEST -> {
                        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                        this.setBoundingBox(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                    }
                    case NORTH -> {
                        this.setBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                        this.setBoundingBox(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                    }
                    case EAST -> {
                        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                        this.setBoundingBox(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                    }
                    case SOUTH -> {
                        this.setBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                        this.setBoundingBox(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                    }
                }
            }
            case TOP -> {
                switch (facing) {
                    case WEST -> {
                        this.setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                        this.setBoundingBox(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                    }
                    case NORTH -> {
                        this.setBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                        this.setBoundingBox(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                    }
                    case EAST -> {
                        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                        this.setBoundingBox(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                    }
                    case SOUTH -> {
                        this.setBoundingBox(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                        this.setBoundingBox(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                        super.addIntersectingBoundingBox(world, x, y, z, box, boxes);
                    }
                }
            }
        }
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        ci.cancel();
    }

    /**
     * @author DanyGames2014
     */
    @Inject(method = "dropStacks", at = @At("HEAD"), cancellable = true)
    public void broom_dropStacks(World world, int x, int y, int z, int meta, float f, CallbackInfo ci) {
        int droppedItemCount = this.getDroppedItemCount(world.field_214);

        for (int i = 0; i < droppedItemCount; ++i) {
            this.dropStack(world, x, y, z, new ItemStack(id, 1, this.getDroppedItemMeta(meta)));
        }
        ci.cancel();
    }
}
