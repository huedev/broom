package net.huedev.broom.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.huedev.broom.util.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.client.InteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.SingleplayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author paulevsGitch
 */
@Mixin(SingleplayerInteractionManager.class)
public class SingleplayerInteractionManagerMixin extends InteractionManager {
    public SingleplayerInteractionManagerMixin(Minecraft minecraft) {
        super(minecraft);
    }

    @WrapOperation(method = "attackBlock", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/Block;onBlockBreakStart(Lnet/minecraft/world/World;IIILnet/minecraft/entity/player/PlayerEntity;)V"
    ))
    private void broom_disableActivation(Block block, World world, int x, int y, int z, PlayerEntity player, Operation<Void> original) {}

    @ModifyConstant(method = "processBlockBreakingAction", constant = @Constant(intValue = 5))
    private int broom_modifyBlockBreakDelay(int constant) {
        ToolMaterial toolMaterial = ToolHelper.getItemToolMaterial(minecraft.player);
        if (toolMaterial == null) {
            return constant;
        }
        return switch (toolMaterial) {
            case IRON, GOLD -> 4;
            case DIAMOND -> 3;
            default -> constant;
        };
    }
}
