package net.huedev.broom.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author paulevsGitch
 */
public class BroomDoubleSlabBlock extends TemplateBlock {
    private final Function<Integer, Integer> textureGetter;
    private Block slabBlock;

    public BroomDoubleSlabBlock(Identifier id, Material material) {
        super(id, material);
        setTranslationKey(id.toString());
        this.textureGetter = side -> this.textureId;
    }

    public BroomDoubleSlabBlock(Identifier id, Block source) {
        super(id, source.material);
        setTranslationKey(id);
        BLOCKS_LIGHT_LUMINANCE[this.id] = BLOCKS_LIGHT_LUMINANCE[source.id] / 2;
        setHardness(source.getHardness());
        setSoundGroup(source.soundGroup);
        this.textureGetter = source::getTexture;
    }

    public void setSlabBlock(Block slabBlock) {
        this.slabBlock = slabBlock;
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        return Collections.singletonList(new ItemStack(slabBlock, 2));
    }

    @Override
    public void addIntersectingBoundingBox(World world, int x, int y, int z, Box box, ArrayList list) {
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addIntersectingBoundingBox(world, x, y, z, box, list);
    }

    @Override
    public int getTexture(int side) {
        return textureGetter.apply(side);
    }
}
