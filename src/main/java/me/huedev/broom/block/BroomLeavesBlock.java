package me.huedev.broom.block;

import me.huedev.broom.util.FloodFillSearch;
import me.huedev.broom.util.ToolHelper;
import me.huedev.broom.util.WorldHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.template.block.TemplateTranslucentBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.world.BlockStateView;

import java.util.*;
import java.util.function.Function;

/**
 * @author paulevsGitch
 */
public class BroomLeavesBlock extends TemplateTranslucentBlock {
    private static final Function<BlockState, BlockState> ACTIVATOR = state -> state.with(BroomBlockProperties.ACTIVE, true);
    private static final Function<BlockState, Boolean> LEAVES_FILTER = state -> state.isIn(BroomBlockTags.LEAVES);
    private static final Function<BlockState, Boolean> LOG_FILTER = state -> state.isIn(BroomBlockTags.LOGS);
    private static final Map<Integer, FloodFillSearch> SEARCH_CACHE = new HashMap<>();
    private final FloodFillSearch search;
    private final int maxDistance;
    protected boolean brokenBySilkTouchTool = false;

    public BroomLeavesBlock(Identifier id) {
        this(id, Material.LEAVES, 6);
    }

    public BroomLeavesBlock(Identifier id, Material material, int maxDistance) {
        super(BlockTemplate.getNextId(), 0, material, false);
        BlockTemplate.onConstructor(this, id);
        setTranslationKey(id);
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
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (this.id == BroomBlocks.APPLE_OAK_LEAVES.id) {
            BlockState state = world.getBlockState(x, y, z);
            boolean isNatural = state.get(BroomBlockProperties.NATURAL);
            boolean isActive = state.get(BroomBlockProperties.ACTIVE);
            BlockState newState = BroomBlocks.OAK_LEAVES.getDefaultState().with(BroomBlockProperties.NATURAL, isNatural);
            newState = newState.with(BroomBlockProperties.ACTIVE, isActive);
            world.setBlockStateWithNotify(x, y, z, newState);
            ItemStack stack = new ItemStack(Item.APPLE, 1);
            dropStack(world, x, y, z, stack);
            return true;
        } else if (this.id == BroomBlocks.CACAO_POD_LEAVES.id) {
            BlockState state = world.getBlockState(x, y, z);
            boolean isNatural = state.get(BroomBlockProperties.NATURAL);
            boolean isActive = state.get(BroomBlockProperties.ACTIVE);
            BlockState newState = BroomBlocks.CACAO_LEAVES.getDefaultState().with(BroomBlockProperties.NATURAL, isNatural);
            newState = newState.with(BroomBlockProperties.ACTIVE, isActive);
            world.setBlockStateWithNotify(x, y, z, newState);
            ItemStack stack = new ItemStack(Item.DYE, 3, 3);
            dropStack(world, x, y, z, stack);
            return true;
        }
        return false;
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        checkLeaves(world, x, y, z, true);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (!world.isRemote) {
            if (ToolHelper.isUsingGoldenTool(player) || ToolHelper.isUsingShears(player)) {
                brokenBySilkTouchTool = true;
            }

            this.dropStacks(world, x, y, z, meta);
        }
    }

    @Override
    @Environment(value = EnvType.CLIENT)
    public int getColor(int meta) {
        return LEAVES.getColor(0);
    }

    @Environment(value = EnvType.CLIENT)
    public int getColorMultiplier(BlockView view, int x, int y, int z) {
        view.method_1781().method_1788(x, z, 1, 1);
        double t = view.method_1781().field_2235[0];
        double w = view.method_1781().field_2236[0];
        return FoliageColors.getColor(t, w);
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
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
        world.setBlockStateWithNotify(x, y, z, States.AIR.get());
        world.method_246(x, y, z);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        if (brokenBySilkTouchTool) {
            brokenBySilkTouchTool = false;
            return Collections.singletonList(new ItemStack(this, 1, 0));
        } else {
            List<ItemStack> dropList = new java.util.ArrayList<>(Collections.emptyList());
            int saplingCount = this.getDroppedItemCount(world.field_214);
            if (saplingCount != 0) {
                if (this.id == BroomBlocks.APPLE_OAK_LEAVES.id) {
                    dropList.add(new ItemStack(BroomBlocks.OAK_SAPLING, 1, 0));
                }
                if (this.id == BroomBlocks.CACAO_LEAVES.id) {
                    dropList.add(new ItemStack(BroomBlocks.CACAO_SAPLING, 1, 0));
                }
            }
            if (this.id == BroomBlocks.APPLE_OAK_LEAVES.id) {
                dropList.add(new ItemStack(Item.APPLE, 1));
            }
            if (this.id == BroomBlocks.CACAO_POD_LEAVES.id) {
                dropList.add(new ItemStack(Item.DYE, 3, 3));
            }
            return dropList;
        }
    }
}
