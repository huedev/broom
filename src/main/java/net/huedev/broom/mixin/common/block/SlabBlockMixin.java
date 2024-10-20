package net.huedev.broom.mixin.common.block;

import net.huedev.broom.block.BroomBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;

@Mixin(SlabBlock.class)
public class SlabBlockMixin extends Block {
    @Shadow private boolean doubleSlab;
    @Unique
    private static final String[] field_2323 = new String[] {"stone", "sandstone", "wooden", "cobblestone"};

    public SlabBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        return Collections.singletonList(new ItemStack(BroomBlocks.getSlabByMeta(meta), this.doubleSlab ? 2 : 1));
    }

    @Inject(method = "onPlaced", at = @At("HEAD"), cancellable = true)
    private void broom_onPlaced(World world, int x, int y, int z, CallbackInfo info) {
        info.cancel();
        int meta = world.getBlockMeta(x, y, z);
        BlockState state = (this.doubleSlab ? BroomBlocks.getDoubleSlabByMeta(meta) : BroomBlocks.getSlabByMeta(meta)).getDefaultState();
        world.setBlockStateWithNotify(x, y, z, state);
    }
}
