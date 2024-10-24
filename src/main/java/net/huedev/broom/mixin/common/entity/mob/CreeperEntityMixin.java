package net.huedev.broom.mixin.common.entity.mob;

import net.huedev.broom.item.BroomItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MonsterEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author Jadestrouble
 */
@Mixin(CreeperEntity.class)
public class CreeperEntityMixin extends MonsterEntity {
    public CreeperEntityMixin(World arg) {
        super(arg);
    }

    @Redirect(method = "onKilledBy", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/CreeperEntity;dropItem(II)Lnet/minecraft/entity/ItemEntity;"))
    public ItemEntity broom_dropAddedRecords(CreeperEntity instance, int id, int count) {
        return instance.dropItem(new ItemStack(BroomItems.MUSIC_DISCS[random.nextInt(BroomItems.MUSIC_DISCS.length)]), 1);
    }
}
