package net.huedev.broom.mixin.common.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DyeItem.class)
public class DyeItemMixin extends Item {
    @Unique
    private static final String[] names = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "light_gray", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"};

    public DyeItemMixin(int id) {
        super(id);
    }

    @Inject(method = "getTranslationKey", at = @At("HEAD"), cancellable = true)
    @Environment(EnvType.CLIENT)
    public void getTranslationKey(ItemStack stack, CallbackInfoReturnable<String> cir) {
        String[] originalKeyParts = super.getTranslationKey().split("[.]");
        cir.setReturnValue(originalKeyParts[0] + "." + names[stack.getDamage()] + "_" + originalKeyParts[1]);
    }

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void broom_growAdditionalPlants(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getDamage() == 15) {
            int blockId = world.getBlockId(x, y, z);
            if (blockId == Block.WHEAT.id) {
                if (!world.isRemote) {
                    int meta = world.getBlockMeta(x, y, z);
                    if (meta < 7) {
                        ((CropBlock)Block.WHEAT).applyFullGrowth(world, x, y, z);
                        --stack.count;
                    }
                }

                cir.setReturnValue(true);
            }

            if (blockId == Block.SUGAR_CANE.id) {
                if (!world.isRemote) {
                    if (world.isAir(x, y + 1, z)) {
                        int plantLength;
                        for(plantLength = 1; world.getBlockId(x, y - plantLength, z) == Block.SUGAR_CANE.id; ++plantLength) {
                        }

                        if (plantLength < 3) {
                            world.setBlock(x, y + 1, z, Block.SUGAR_CANE.id);
                            world.setBlockMeta(x, y, z, 0);
                            --stack.count;
                        }
                    }
                }

                cir.setReturnValue(true);
            }

            if (blockId == Block.CACTUS.id) {
                if (!world.isRemote) {
                    if (world.isAir(x, y + 1, z)) {
                        int plantLength;
                        for(plantLength = 1; world.getBlockId(x, y - plantLength, z) == Block.CACTUS.id; ++plantLength) {
                        }

                        if (plantLength < 3) {
                            world.setBlock(x, y + 1, z, Block.CACTUS.id);
                            world.setBlockMeta(x, y, z, 0);
                            --stack.count;
                        }
                    }
                }

                cir.setReturnValue(true);
            }
        }
    }
}
