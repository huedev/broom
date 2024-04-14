package me.huedev.broom.block;

import me.huedev.broom.block.BroomBlockProperties.TopBottom;
import me.huedev.broom.util.WorldHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.world.BlockStateView;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * @author paulevsGitch
 */
public class BroomSlabBlock extends TemplateBlock {
    private final Function<Integer, Integer> textureGetter;
    private Block doubleSlabBlock;

    public BroomSlabBlock(Identifier id, Material material) {
        super(id, material);
        setTranslationKey(id.toString());
        this.textureGetter = side -> this.textureId;
        BLOCKS_ALLOW_VISION[this.id] = true;
        setDefaultState(getDefaultState().with(BroomBlockProperties.TOP_BOTTOM, TopBottom.BOTTOM));
    }

    public BroomSlabBlock(Identifier id, Block source) {
        super(id, source.material);
        setTranslationKey(id.toString());
        BLOCKS_LIGHT_LUMINANCE[this.id] = BLOCKS_LIGHT_LUMINANCE[source.id] / 2;
        setHardness(source.getHardness());
        setSoundGroup(source.soundGroup);
        this.textureGetter = source::getTexture;
        BLOCKS_ALLOW_VISION[this.id] = true;
        setDefaultState(getDefaultState().with(BroomBlockProperties.TOP_BOTTOM, TopBottom.BOTTOM));
    }

    @Override
    public void appendProperties(Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BroomBlockProperties.TOP_BOTTOM);
    }

    public void setDoubleSlabBlock(Block doubleSlabBlock) {
        this.doubleSlabBlock = doubleSlabBlock;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        World world = context.getWorld();
        Direction side = context.getSide();
        PlayerEntity player = context.getPlayer();

        if (player != null && !player.method_1373()) {
            if (side.equals(Direction.DOWN)) {
                return getDefaultState().with(BroomBlockProperties.TOP_BOTTOM, TopBottom.TOP);
            } else if (side.equals(Direction.UP)) {
                return getDefaultState().with(BroomBlockProperties.TOP_BOTTOM, TopBottom.BOTTOM);
            } else {
                HitResult hit = WorldHelper.raycast(world, player);
                float dy = (float) (hit.pos.y - hit.blockY);
                return getDefaultState().with(BroomBlockProperties.TOP_BOTTOM, dy > 0.5F ? TopBottom.TOP : TopBottom.BOTTOM);
            }
        }
        return getDefaultState().with(BroomBlockProperties.TOP_BOTTOM, TopBottom.BOTTOM);
    }

    @Override
    public void updateBoundingBox(BlockView view, int x, int y, int z) {
        if (!(view instanceof BlockStateView bsView)) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            return;
        }

        BlockState state = bsView.getBlockState(x, y, z);
        if (!state.isOf(this)) return;
        TopBottom type = state.get(BroomBlockProperties.TOP_BOTTOM);

        if (type == TopBottom.TOP) {
            this.setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    @Override
    public void addIntersectingBoundingBox(World world, int x, int y, int z, Box box, ArrayList list) {
        updateBoundingBox(world, x, y, z);
        super.addIntersectingBoundingBox(world, x, y, z, box, list);
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        ItemStack stack = player.getHand();
        if (stack == null) return false;

        Item item = stack.getItem();
        if (!(item instanceof BlockItem blockItem)) return false;

        if (blockItem.getBlock() != this) return false;

        BlockState state = world.getBlockState(x, y, z);
        if (!state.isOf(this)) return false;

        HitResult hit = WorldHelper.raycast(world, player);
        if (hit == null || hit.type != HitResultType.BLOCK) return false;

        double dx = hit.pos.x - x;
        double dy = hit.pos.y - y;
        double dz = hit.pos.z - z;

        if (dx < 0 || dx > 1 || dy < 0 || dy > 1 || dz < 0 || dz > 1) return false;

        if (Math.abs(dy - 0.5) > 0.0001) return false;

        BlockState fullBlock = this.doubleSlabBlock.getDefaultState();

        world.setBlockStateWithNotify(x, y, z, fullBlock);
        world.playSound(x + 0.5, y + 0.5, z + 0.5, this.soundGroup.getSound(), 1.0F, 1.0F);
        world.method_246(x, y, z);

        stack.count--;

        return true;
    }

    @Override
    @Environment(value = EnvType.CLIENT)
    public boolean isSideVisible(BlockView view, int x, int y, int z, int side) {
        if (!(view instanceof BlockStateView bsView)) {
            return super.isSideVisible(view, x, y, z, side);
        }

        Direction face = Direction.byId(side);
        BlockState selfState = bsView.getBlockState(x, y, z);

        if (selfState.getBlock() instanceof BroomSlabBlock) {
            TopBottom selfType = selfState.get(BroomBlockProperties.TOP_BOTTOM);
            if ((side == 0 && selfType == TopBottom.TOP) || (side == 1 && selfType == TopBottom.BOTTOM)) {
                return true;
            }
        }

        BlockState sideState = bsView.getBlockState(x - face.getOffsetX(), y - face.getOffsetY(), z - face.getOffsetZ());

        if (sideState.getBlock() instanceof BroomSlabBlock && selfState.getBlock() instanceof BroomSlabBlock) {
            TopBottom selfType = selfState.get(BroomBlockProperties.TOP_BOTTOM);
            TopBottom sideType = selfState.get(BroomBlockProperties.TOP_BOTTOM);
            return selfType == sideType;
        }

        return super.isSideVisible(view, x, y, z, side);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void setupRenderBoundingBox() {
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    @Override
    public int getTexture(int side) {
        return textureGetter.apply(side);
    }
}
