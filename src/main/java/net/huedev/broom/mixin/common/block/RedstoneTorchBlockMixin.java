package net.huedev.broom.mixin.common.block;

import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RedstoneTorchBlock.class)
public class RedstoneTorchBlockMixin extends TorchBlock {
    @Shadow private boolean lit;

    public RedstoneTorchBlockMixin(int id, int textureId) {
        super(id, textureId);
    }

    @Inject(method = "onPlaced", at = @At("HEAD"))
    public void broom_onPlaced(World world, int x, int y, int z, CallbackInfo ci) {
        if (this.lit) {
            world.notifyNeighbors(x, y, z, this.id);
        }
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    public void broom_onBreak(World world, int x, int y, int z, CallbackInfo ci) {
        if (this.lit) {
            world.notifyNeighbors(x, y, z, this.id);
        }
    }
}
