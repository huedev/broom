package net.huedev.broom.mixin.client;

import net.minecraft.client.InteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author paulevsGitch
 */
@Mixin(InteractionManager.class)
public class InteractionManagerMixin {
    @Shadow
    @Final
    protected Minecraft minecraft;

    @Redirect(method = "interactBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockId(III)I"))
    public int broom_shiftPlacing(World world, int x, int y, int z) {
        if (this.minecraft.player.isSneaking() && this.minecraft.player.getHand() != null) {
            return 0;
        }
        return world.getBlockId(x, y, z);
    }
}
