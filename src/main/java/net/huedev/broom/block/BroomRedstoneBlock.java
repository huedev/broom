package net.huedev.broom.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class BroomRedstoneBlock extends TemplateBlock {
    public BroomRedstoneBlock(Identifier id, Material material) {
        super(id, material);
        setTranslationKey(id);
        setHardness(5.0F);
        setSoundGroup(Block.METAL_SOUND_GROUP);
        ignoreMetaUpdates();
        this.setTickRandomly(true);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        super.onPlaced(world, x, y, z);
        world.notifyNeighbors(x, y, z, this.id);
        world.notifyNeighbors(x, y - 1, z, this.id);
        world.notifyNeighbors(x, y + 1, z, this.id);
        world.notifyNeighbors(x - 1, y, z, this.id);
        world.notifyNeighbors(x + 1, y, z, this.id);
        world.notifyNeighbors(x, y, z - 1, this.id);
        world.notifyNeighbors(x, y, z + 1, this.id);
    }

    @Override
    public void onBreak(World world, int x, int y, int z) {
        super.onBreak(world, x, y, z);
        world.notifyNeighbors(x, y, z, this.id);
        world.notifyNeighbors(x, y - 1, z, this.id);
        world.notifyNeighbors(x, y + 1, z, this.id);
        world.notifyNeighbors(x - 1, y, z, this.id);
        world.notifyNeighbors(x + 1, y, z, this.id);
        world.notifyNeighbors(x, y, z - 1, this.id);
        world.notifyNeighbors(x, y, z + 1, this.id);
    }

    @Override
    public boolean isEmittingRedstonePowerInDirection(BlockView blockView, int x, int y, int z, int direction) {
        return true;
    }

    @Override
    public boolean canTransferPowerInDirection(World world, int x, int y, int z, int direction) {
        return true;
    }

    @Override
    public boolean canEmitRedstonePower() {
        return true;
    }
}
