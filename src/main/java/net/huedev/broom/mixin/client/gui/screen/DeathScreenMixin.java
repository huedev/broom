package net.huedev.broom.mixin.client.gui.screen;

import net.minecraft.client.gui.screen.DeathScreen;
import net.modificationstation.stationapi.api.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author DanyGames2014
 */
@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    @ModifyConstant(method = "render", constant = @Constant(stringValue = "Score: &e"), require = 0)
    public String broom_fixFormattingCharacter(String constant){
        return constant.replace('&', Formatting.FORMATTING_CODE_PREFIX);
    }
}
