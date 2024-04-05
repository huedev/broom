package me.huedev.broom.mixin.common;

import me.huedev.broom.util.ToolHelper;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(DeadBushBlock.class)
public class DeadBushBlockMixin extends PlantBlock {
    @Unique
    private boolean brokenByShears = false;

    public DeadBushBlockMixin(int id, int textureId) {
        super(id, textureId);
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
    public int getDroppedItemCount(Random random) {
        if (brokenByShears) {
            return 1;
        }
        return random.nextInt(3);
    }

    @Inject(at = @At("RETURN"), method = "getDroppedItemId", cancellable = true)
    public void broom_getDroppedItemId(int blockMeta, Random random, CallbackInfoReturnable<Integer> cir) {
        if (brokenByShears) {
            cir.setReturnValue(this.id);
            brokenByShears = false;
        } else {
            cir.setReturnValue(Item.STICK.id);
        }
    }
}
