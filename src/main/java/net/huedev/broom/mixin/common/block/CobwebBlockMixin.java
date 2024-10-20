package net.huedev.broom.mixin.common.block;

import net.huedev.broom.mixin.common.entity.EntityAccessor;
import net.huedev.broom.util.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.CobwebBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CobwebBlock.class)
public class CobwebBlockMixin extends Block {
    @Unique
    private boolean brokenByShears = false;

    @Unique
    private boolean brokenBySword = false;

    public CobwebBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Inject(method = "onEntityCollision", at = @At("HEAD"))
    public void broom_negateFallDamage(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {
        ((EntityAccessor) entity).broom_setFallDistance(0);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, int meta) {
        if (ToolHelper.isUsingShears(player)) {
            brokenByShears = true;
            player.inventory.getSelectedItem().damage(1, player);
        } else if (ToolHelper.isUsingSword(player)) {
            brokenBySword = true;
        }

        if (player != null) {
            player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
            this.dropStacks(world, x, y, z, meta);
        }
    }

    @Inject(at = @At("RETURN"), method = "getDroppedItemId", cancellable = true)
    public void broom_getDroppedItemId(int blockMeta, Random random, CallbackInfoReturnable<Integer> cir) {
        if (brokenByShears) {
            cir.setReturnValue(this.id);
            brokenByShears = false;
            brokenBySword = false;
        } else if (brokenBySword) {
            cir.setReturnValue(Item.STRING.id);
            brokenByShears = false;
            brokenBySword = false;
        }
    }
}
