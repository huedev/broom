package me.huedev.broom.block;

import me.huedev.broom.util.FloodFillSearch;
import me.huedev.broom.util.WorldHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.class_307;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.stat.Stats;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.world.BlockStateView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

/**
 * @author paulevs
 */
public class BroomLeavesBlock extends class_307 implements BlockTemplate {
    private static final Function<BlockState, BlockState> ACTIVATOR = state -> state.with(BroomBlockProperties.ACTIVE, true);
    private static final Function<BlockState, Boolean> LEAVES_FILTER = state -> state.isIn(BroomBlockTags.LEAVES);
    private static final Function<BlockState, Boolean> LOG_FILTER = state -> state.isIn(BroomBlockTags.LOGS);
    private static final Map<Integer, FloodFillSearch> SEARCH_CACHE = new HashMap<>();
    private final FloodFillSearch search;
    private final int maxDistance;

    public BroomLeavesBlock(Identifier id) {
        this(id, Material.LEAVES, 5);
    }

    public BroomLeavesBlock(Identifier id, Material material, int maxDistance) {
        super(BlockTemplate.getNextId(), 0, material, false);
        BlockTemplate.onConstructor(this, id);
        setTranslationKey(id.toString());
        setHardness(LEAVES.getHardness());
        setSoundGroup(DIRT_SOUND_GROUP);
        disableTrackingStatistics();
        ignoreMetaUpdates();
        this.maxDistance = maxDistance;
        this.search = SEARCH_CACHE.computeIfAbsent(maxDistance, FloodFillSearch::new);
        setDefaultState(getDefaultState().with(BroomBlockProperties.NATURAL, true).with(BroomBlockProperties.ACTIVE, false));
        setOpacity(4);
        setTickRandomly(true);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BroomBlockProperties.NATURAL, BroomBlockProperties.ACTIVE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState().with(BroomBlockProperties.NATURAL, false);
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int blockId) {
        checkLeaves(world, x, y, z, true);
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        checkLeaves(world, x, y, z, false);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (!world.isRemote) {
            ItemStack stack = player.getHand();
            if (stack != null && stack.getItem() instanceof ShearsItem) {
                if (this.isTrackingStatistics()) {
                    player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
                }
                this.dropStack(world, x, y, z, new ItemStack(this));
                return;
            }
        }
        super.afterBreak(world, player, x, y, z, meta);
    }

    @Override
    @Environment(value = EnvType.CLIENT)
    public boolean isSideVisible(BlockView view, int x, int y, int z, int side) {
        if (view instanceof BlockStateView blockStateView) {
            BlockState state = blockStateView.getBlockState(x, y, z);
            if (state.getBlock() instanceof LeavesBlock || !state.isOpaque()) {
                return true;
            }
            return super.isSideVisible(view, x, y, z, side);
        }
        return super.isSideVisible(view, x, y, z, side);
    }

    private void checkLeaves(World world, int x, int y, int z, boolean force) {
        if (world.isRemote) return;
        BlockState state = world.getBlockState(x, y, z);

        if (!state.isOf(this)) return;
        if (!state.get(BroomBlockProperties.NATURAL)) return;

        boolean active = state.get(BroomBlockProperties.ACTIVE);
        if (!force && !active) return;

        WorldHelper.setBlockSilent(world, x, y, z, state.with(BroomBlockProperties.ACTIVE, false));

        int radius = search.search(world, x, y, z, LOG_FILTER, LEAVES_FILTER);
        if (radius > 0 && radius <= maxDistance) return;

        if (force && !active) {
            search.transform(world, x, y, z, LEAVES_FILTER, ACTIVATOR);
        }

        dropStacks(world, x, y, z, 0);
        world.setBlockState(x, y, z, States.AIR.get());
        world.method_246(x, y, z);

        for (byte i = 0; i < 6; i++) {
            Direction side = Direction.byId(i);
            int px = x + side.getOffsetX();
            int py = y + side.getOffsetY();
            int pz = z + side.getOffsetZ();
            state = world.getBlockState(px, py, pz);
            if (state.getBlock() instanceof BroomLeavesBlock) {
                world.method_216(px, py, pz, state.getBlock().id, 10 + world.field_214.nextInt(20));
            }
        }
    }
}
