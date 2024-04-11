package me.huedev.broom.mixin.common.block;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Telvarost
 */
@Mixin(SignBlock.class)
public abstract class SignBlockMixin extends BlockWithEntity {
    protected SignBlockMixin(int i, int j, Material arg) {
        super(i, j, arg);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity user) {
        SignBlockEntity signBlockEntity = (SignBlockEntity)world.method_1777(x, y, z);
        if (signBlockEntity != null) {
            PlayerEntity player = PlayerHelper.getPlayerFromGame();
            if (player == null) {
                signBlockEntity.setEditable(true);
            }
            user.method_489(signBlockEntity);
        }
        return true;
    }
}
