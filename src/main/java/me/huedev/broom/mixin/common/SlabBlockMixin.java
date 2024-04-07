package me.huedev.broom.mixin.common;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.SlabBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SlabBlock.class)
public class SlabBlockMixin extends Block {
    @Unique
    private static final String[] field_2323 = new String[] {"stone", "sandstone", "wooden", "cobblestone"};

    public SlabBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }
}
