package net.huedev.broom.block;

import net.huedev.broom.util.ToolHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.stat.Stats;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.template.block.TemplatePlantBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

public class BroomTallPlantBlock extends TemplatePlantBlock {
    @Unique
    private boolean brokenByShears = false;

    public BroomTallPlantBlock(Identifier id, int texture) {
        super(id, texture);
        setTranslationKey(id);
        setSoundGroup(DIRT_SOUND_GROUP);
        float halfFaceLength = 0.4F;
        this.setBoundingBox(0.5F - halfFaceLength, 0.0F, 0.5F - halfFaceLength, 0.5F + halfFaceLength, halfFaceLength * 2.0F, 0.5F + halfFaceLength);
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return this.canPlantOn(world.getBlockState(x, y - 1, z));
    }

    protected boolean canPlantOn(BlockState state) {
        return state.isIn(BroomBlockTags.DIRT);
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        this.breakIfInvalid(world, x, y, z);
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        if (!world.isRemote) {
            this.breakIfInvalid(world, x, y, z);
        }
    }

    @Unique
    protected void breakIfInvalid(World world, int x, int y, int z) {
        if (!this.canGrow(world, x, y, z)) {
            this.dropStacks(world, x, y, z, world.getBlockMeta(x, y, z));
            world.setBlock(x, y, z, 0);
        }
    }

    @Override
    public boolean canGrow(World world, int x, int y, int z) {
        return (world.getBrightness(x, y, z) >= 8 || world.hasSkyLight(x, y, z)) && this.canPlantOn(world.getBlockState(x, y - 1, z));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public int getColorMultiplier(BlockView blockView, int x, int y, int z) {
        long var6 = (long)(x * 3129871 + z * 6129781 + y);
        var6 = var6 * var6 * 42317861L + var6 * 11L;
        x = (int)((long)x + (var6 >> 14 & 31L));
        y = (int)((long)y + (var6 >> 19 & 31L));
        z = (int)((long)z + (var6 >> 24 & 31L));
        blockView.method_1781().getBiomesInArea(x, z, 1, 1);
        double var8 = blockView.method_1781().temperatureMap[0];
        double var10 = blockView.method_1781().downfallMap[0];
        return GrassColors.getColor(var8, var10);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (ToolHelper.isUsingShears(player)) {
            brokenByShears = true;
            player.inventory.getSelectedItem().damage(1, player);
        }

        if (player != null) {
            player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
            this.dropStacks(world, x, y, z, meta);
        }
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        if (brokenByShears) {
            brokenByShears = false;
            return this.id;
        } else {
            return random.nextInt(8) == 0 ? Item.SEEDS.id : -1;
        }
    }
}
