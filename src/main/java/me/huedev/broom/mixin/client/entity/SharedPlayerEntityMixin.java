package me.huedev.broom.mixin.client.entity;

import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ClientPlayerEntity.class, PlayerEntity.class})
public abstract class SharedPlayerEntityMixin {
    /**
     * @author telvarost
     */
    /*
    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    private void broom_dropSelectedItem(CallbackInfo ci) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            Minecraft minecraft = MinecraftAccessor.broom_getInstance();
            PlayerEntity player = (PlayerEntity) (Object) this;

            minecraft.interactionManager.clickSlot(0, 36 + player.inventory.selectedSlot, 0, false, minecraft.player);
            minecraft.interactionManager.clickSlot(0, -999, 0, false, minecraft.player);
            ci.cancel();
        }
    }
    */
}
