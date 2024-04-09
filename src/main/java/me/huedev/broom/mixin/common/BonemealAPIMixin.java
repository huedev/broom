package me.huedev.broom.mixin.common;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.bonemeal.BonemealAPI;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.collection.WeightedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(BonemealAPI.class)
public class BonemealAPIMixin {
    @Shadow @Final private static Map<BlockState, WeightedList<Feature>> PLACERS_BLOCK;

    @Shadow @Final private static Map<TagKey<Block>, WeightedList<Feature>> PLACERS_TAG;

    @Shadow @Final private static WeightedList<Feature> CACHE;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void broom_clearBonemealAPIPlants(CallbackInfo ci, @Local BlockState grass) {
        PLACERS_TAG.clear();
        PLACERS_BLOCK.clear();
        CACHE.clear();
    }
}
