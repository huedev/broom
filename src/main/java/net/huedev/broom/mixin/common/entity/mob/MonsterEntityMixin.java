package net.huedev.broom.mixin.common.entity.mob;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.mob.MonsterEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MonsterEntity.class)
public class MonsterEntityMixin extends MobEntity {
    public MonsterEntityMixin(World arg) {
        super(arg);
    }

    @Override
    public boolean canSpawn() {
        int var1 = MathHelper.floor(this.x);
        int var2 = MathHelper.floor(this.boundingBox.minY);
        int var3 = MathHelper.floor(this.z);
        int skyLight = this.world.getBrightness(LightType.SKY, var1, var2, var3);
        int randomInt = this.random.nextInt(32);
        if (skyLight > randomInt) {
            return false;
        } else {
            int internalLight = this.world.getLightLevel(var1, var2, var3);
            int blockLight = this.world.getBrightness(LightType.BLOCK, var1, var2, var3);
            if (this.world.isThundering()) {
                int var5 = this.world.ambientDarkness;
                this.world.ambientDarkness = 10;
                internalLight = this.world.getLightLevel(var1, var2, var3);
                this.world.ambientDarkness = var5;
            }

            int internalSkyLightMinimum = 4;
            return internalLight <= internalSkyLightMinimum && blockLight == 0 && super.canSpawn();
        }
    }
}
