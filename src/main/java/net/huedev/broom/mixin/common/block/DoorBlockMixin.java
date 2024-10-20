package net.huedev.broom.mixin.common.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.huedev.broom.util.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoorBlock.class)
public abstract class DoorBlockMixin extends Block {
    @Shadow public abstract void method_837(World world, int x, int y, int z, boolean bl);

    public DoorBlockMixin(int id, Material material) {
        super(id, material);
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        if (y >= 127) {
            return false;
        } else {
            return (world.method_1780(x, y - 1, z) || WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z)) && super.canPlaceAt(world, x, y, z) && super.canPlaceAt(world, x, y + 1, z);
        }
    }

    @ModifyExpressionValue(
            method = "neighborUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;method_1780(III)Z",
                    ordinal = 0
            )
    )
    private boolean broom_preventBreakingOnUpdate(boolean original, @Local(argsOnly = true) World world, @Local(ordinal = 0, argsOnly = true) int x, @Local(ordinal = 1, argsOnly = true) int y, @Local(ordinal = 2, argsOnly = true) int z) {
        return original || WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z);
    }

    /*
    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        int var6 = world.getBlockMeta(x, y, z);
        if ((var6 & 8) != 0) {
            if (world.getBlockId(x, y - 1, z) != this.id) {
                world.setBlock(x, y, z, 0);
            }

            if (id > 0 && Block.BLOCKS[id].canEmitRedstonePower()) {
                this.neighborUpdate(world, x, y - 1, z, id);
            }
        } else {
            boolean var7 = false;
            if (world.getBlockId(x, y + 1, z) != this.id) {
                world.setBlock(x, y, z, 0);
                var7 = true;
            }

            if (!world.method_1780(x, y - 1, z) && !WorldHelper.isBlockStateFloorSupport(world, x, y - 1, z)) {
                world.setBlock(x, y, z, 0);
                var7 = true;
                if (world.getBlockId(x, y + 1, z) == this.id) {
                    world.setBlock(x, y + 1, z, 0);
                }
            }

            if (var7) {
                if (!world.isRemote) {
                    this.dropStacks(world, x, y, z, var6);
                }
            } else if (id > 0 && Block.BLOCKS[id].canEmitRedstonePower()) {
                boolean var8 = world.method_265(x, y, z) || world.method_265(x, y + 1, z);
                this.method_837(world, x, y, z, var8);
            }
        }
    }
    */

    @Inject(method = "onBlockBreakStart", at = @At("HEAD"), cancellable = true)
    private void broom_cancelInteract(World world, int x, int y, int z, PlayerEntity player, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "onUse",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;method_173(Lnet/minecraft/entity/player/PlayerEntity;IIIII)V"
            ),
            cancellable = true
    )
    private void broom_onUseOpenCloseSound(World world, int x, int y, int z, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        int meta = world.getBlockMeta(x, y, z);
        boolean opened = (meta & 4) != 0;
        if (!world.isRemote) {
            if (opened) {
                world.method_173(null, 1006, x, y, z, 0);
            } else {
                world.method_173(null, 1007, x, y, z, 0);
            }
        }
        cir.setReturnValue(true);
    }

    @Inject(
            method = "method_837",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;method_173(Lnet/minecraft/entity/player/PlayerEntity;IIIII)V"
            ),
            cancellable = true
    )
    private void broom_onRedstoneOpenCloseSound(World world, int x, int y, int z, boolean bl, CallbackInfo ci) {
        int meta = world.getBlockMeta(x, y, z);
        boolean opened = (meta & 4) != 0;
        if (!world.isRemote) {
            if (opened) {
                world.method_173(null, 1006, x, y, z, 0);
            } else {
                world.method_173(null, 1007, x, y, z, 0);
            }
        }
        ci.cancel();
    }
}
