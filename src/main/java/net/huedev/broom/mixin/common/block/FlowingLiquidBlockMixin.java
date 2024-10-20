package net.huedev.broom.mixin.common.block;

import net.minecraft.block.FlowingLiquidBlock;
import net.minecraft.block.Material;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author DanyGames2014
 */
@Mixin(FlowingLiquidBlock.class)
public class FlowingLiquidBlockMixin {
    @Redirect(
            method = "onTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockMeta(III)I"
            ),
            require = 0
    )
    private int broom_allowWaterSourcePropagation(World world, int x, int y, int z) {
        return world.getBlockMeta(x, y - 1, z);
    }

    @Redirect(
            method = "onTick",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/FlowingLiquidBlock;material:Lnet/minecraft/block/Material;",
                    opcode = Opcodes.GETFIELD,
                    ordinal = 3
            )
    )
    private Material broom_allowLavaToDisappear(FlowingLiquidBlock block) {
        return Material.WATER;
    }
}
