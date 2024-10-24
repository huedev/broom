package net.huedev.broom.mixin.common.entity.vehicle;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author DanyGames2014
 */
@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity {
    public BoatEntityMixin(World world) {
        super(world);
    }

    @Inject(
            method = "interact",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;setVehicle(Lnet/minecraft/entity/Entity;)V",
                    shift = At.Shift.AFTER
            )
    )
    public void broom_fixDismountPosition(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (player.passenger == null) {
            player.setPosition(player.x, player.y + 0.01, player.z);
        }
    }

    @Redirect(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/BoatEntity;dropItem(IIF)Lnet/minecraft/entity/ItemEntity;"
            )
    )
    private ItemEntity broom_changeDamageItemId(BoatEntity instance, int id, int count, float offset) {
        return this.dropItem(Item.BOAT.id, 1, 0.0F);
    }

    @ModifyConstant(method = "damage", constant = @Constant(intValue = 3))
    private int broom_changeDamageDroppedItemCount(int constant) {
        return 1;
    }

    @ModifyConstant(method = "damage", constant = @Constant(intValue = 2))
    private int broom_changeDamageSecondaryDroppedItemCount(int constant) {
        return 0;
    }

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/BoatEntity;dropItem(IIF)Lnet/minecraft/entity/ItemEntity;"
            )
    )
    private ItemEntity broom_changeCrashItemId(BoatEntity instance, int id, int count, float offset) {
        return this.dropItem(Item.BOAT.id, 1, 0.0F);
    }

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 3))
    private int broom_changeCrashDroppedItemCount(int constant) {
        return 1;
    }

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 2, ordinal = 2))
    private int broom_changeCrashSecondaryDroppedItemCount(int constant) {
        return 0;
    }
}
