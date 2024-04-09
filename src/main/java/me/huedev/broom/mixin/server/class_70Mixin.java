package me.huedev.broom.mixin.server;

import net.minecraft.class_70;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(class_70.class)
public class class_70Mixin {
    @Shadow
    public PlayerEntity field_2309;

    @Redirect(method = "method_1832", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockId(III)I"))
    public int broom_shiftPlacing(World world, int x, int y, int z) {
        if (this.field_2309.method_1373() && this.field_2309.getHand() != null) {
            return 0;
        }
        return world.getBlockId(x, y, z);
    }
}