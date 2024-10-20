package net.huedev.broom.mixin.common.block;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SignBlock.class)
public abstract class SignBlockMixin extends BlockWithEntity {
    protected SignBlockMixin(int i, int j, Material arg) {
        super(i, j, arg);
    }

    /**
     * @author Telvarost
     */
    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity user) {
        SignBlockEntity signBlockEntity = (SignBlockEntity)world.getBlockEntity(x, y, z);
        if (signBlockEntity != null) {
            PlayerEntity player = PlayerHelper.getPlayerFromGame();
            if (player == null) {
                signBlockEntity.setEditable(true);
            }
            user.openEditSignScreen(signBlockEntity);
        }
        return true;
    }

    @ModifyVariable(method = "updateBoundingBox", at = @At("STORE"), ordinal = 0)
    private float broom_fixStartingY(float value) {
        return 0.25F;
    }

    @ModifyVariable(method = "updateBoundingBox", at = @At("STORE"), ordinal = 1)
    private float broom_fixEndingY(float value) {
        return 0.75F;
    }
}
