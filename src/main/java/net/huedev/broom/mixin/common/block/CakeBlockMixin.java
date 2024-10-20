package net.huedev.broom.mixin.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin(CakeBlock.class)
public class CakeBlockMixin extends Block {
    @Unique
    private static final Random random = new Random();

    public CakeBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "tryEat", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;heal(I)V"))
    public void broom_playSoundOnEat(World world, int x, int y, int z, PlayerEntity player, CallbackInfo ci) {
        world.playSound(player, "random.eat", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        if (meta == 0) {
            return Collections.singletonList(new ItemStack(Item.CAKE, 1));
        }
        return null;
    }
}
