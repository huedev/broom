package net.huedev.broom.mixin.common.block;

import net.huedev.broom.mixin.common.entity.PlayerEntityAccessor;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(BedBlock.class)
public abstract class BedBlockMixin extends Block {
    public BedBlockMixin(int id, int textureId, Material material) {
        super(id, textureId, material);
    }

    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;method_495(III)Lnet/minecraft/entity/player/SleepAttemptResult;"))
    public void broom_setSpawn(World world, int x, int y, int z, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (!player.world.isRemote) {
            Vec3i currentRespawnPosition = ((PlayerEntityAccessor) player).getField_516();
            Vec3i newRespawnPosition = new Vec3i(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
            if (!Objects.equals(currentRespawnPosition, newRespawnPosition)) {
                player.method_490(getTranslationKey() + "." + "respawn_set");
                ((PlayerEntityAccessor) player).broom_setRespawnPosition(newRespawnPosition);
            }
        }
    }
}
