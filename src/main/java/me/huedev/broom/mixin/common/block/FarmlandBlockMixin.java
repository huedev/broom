package me.huedev.broom.mixin.common.block;

import me.huedev.broom.block.BroomBlockTags;
import me.huedev.broom.block.BroomBlocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {
    @Inject(method = "onSteppedOn", at = @At("HEAD"), cancellable = true)
    private void broom_cancelTrample(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "neighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlock(IIII)Z"), cancellable = true)
    private void broom_cancelBlockTrample(World world, int x, int y, int z, int id, CallbackInfo ci) {
        BlockState state = world.getBlockState(x, y + 1, z);
        if (state.isIn(BroomBlockTags.MAINTAINS_FARMLAND)) {
            ci.cancel();
        }
    }
}
