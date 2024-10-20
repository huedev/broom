package net.huedev.broom.mixin.client.network;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.network.packet.s2c.play.EntityDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.PaintingEntitySpawnS2CPacket;
import net.minecraft.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientNetworkHandler.class)
public class ClientNetworkHandlerMixin {
    @Shadow private ClientWorld world;

    @Inject(method = "onPaintingEntitySpawn", at = @At("TAIL"))
    public void broom_playBlockPlaceSound(PaintingEntitySpawnS2CPacket par1, CallbackInfo ci, @Local PaintingEntity painting) {
        this.world.playSound((double)((float)painting.x + 0.5F), (double)((float)painting.y + 0.5F), (double)((float)painting.z + 0.5F), Block.WOOD_SOUND_GROUP.getSound(), (Block.WOOD_SOUND_GROUP.getVolume() + 1.0F) / 2.0F, Block.WOOD_SOUND_GROUP.getPitch() * 0.8F);
    }

    @Inject(method = "onEntityDestroy", at = @At("HEAD"), cancellable = true)
    public void broom_playBlockPlaceSound(EntityDestroyS2CPacket arg, CallbackInfo ci) {
        Entity entity = this.world.removeEntity(arg.id);
        if (entity instanceof PaintingEntity) {
            this.world.playSound((double) ((float) entity.x + 0.5F), (double) ((float) entity.y + 0.5F), (double) ((float) entity.z + 0.5F), Block.WOOD_SOUND_GROUP.getSound(), (Block.WOOD_SOUND_GROUP.getVolume() + 1.0F) / 2.0F, Block.WOOD_SOUND_GROUP.getPitch() * 0.8F);
        }
        ci.cancel();
    }
}
