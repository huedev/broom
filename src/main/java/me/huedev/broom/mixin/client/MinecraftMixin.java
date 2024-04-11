package me.huedev.broom.mixin.client;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Util;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author DanyGames2014
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    public Canvas canvas;

    @Shadow
    public int displayWidth;

    @Shadow
    public int displayHeight;

    @Shadow
    public HitResult field_2823;

    @Shadow
    public World world;

    @Redirect(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/Display;create()V"
            ),
            require = 0
    )
    public void broom_bitDepthFix() throws LWJGLException {
        Display.create(new PixelFormat().withDepthBits(24));
    }

    @Redirect(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/awt/Canvas;getWidth()I",
                    ordinal = 1
            ),
            remap = false
    )
    public int broom_fixWidth(Canvas canvas) {
        AffineTransform transform = canvas.getGraphicsConfiguration().getDefaultTransform();
        return (int) Math.ceil(canvas.getParent().getWidth() * transform.getScaleX());
    }

    @Redirect(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/awt/Canvas;getHeight()I",
                    ordinal = 1
            ),
            remap = false
    )
    public int broom_fixHeight(Canvas canvas) {
        AffineTransform transform = canvas.getGraphicsConfiguration().getDefaultTransform();
        return (int) Math.ceil(canvas.getParent().getHeight() * transform.getScaleY());
    }

    @Inject(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;method_2108(II)V"
            )
    )
    public void broom_fixCanvasSize(CallbackInfo ci) {
        this.canvas.setBounds(0, 0, this.displayWidth, this.displayHeight);
    }

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;method_2111(J)V"))
    private void broom_disableDebugGraph(Minecraft instance, long l) {

    }

    @Unique
    public int broom_getPickBlockId(int pickedBlockId, int blockId, int blockMeta) {
        if (blockId == Block.PISTON_HEAD.id) {
            if (blockMeta < 8) {
                return Block.PISTON.id;
            } else {
                return Block.STICKY_PISTON.id;
            }
        }

        return broom_pickBlockLookupMap.getOrDefault(blockId, pickedBlockId);
    }

    @Unique
    public int broom_getPickEntityId(int pickedEntityId, Entity pickedEntity) {
        if (pickedEntity instanceof PaintingEntity) {
            return Item.PAINTING.id;
        }

        if (pickedEntity instanceof BoatEntity) {
            return Item.BOAT.id;
        }

        if (pickedEntity instanceof MinecartEntity minecart) {
            return switch (minecart.field_2275) {
                case 1 -> Item.CHEST_MINECART.id;
                case 2 -> Item.FURNACE_MINECART.id;
                default -> Item.MINECART.id;
            };
        }
        return pickedEntityId;
    }

    @ModifyVariable(
            method = "method_2103",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/entity/player/ClientPlayerEntity;",
                    opcode = Opcodes.GETFIELD
            ),
            index = 1
    )
    public int broom_pickBlockId(int pickedId) {
        switch (this.field_2823.type) {
            case BLOCK -> {
                return broom_getPickBlockId(
                        pickedId,
                        world.getBlockId(this.field_2823.blockX, this.field_2823.blockY, this.field_2823.blockZ),
                        world.getBlockMeta(this.field_2823.blockX, this.field_2823.blockY, this.field_2823.blockZ)
                );
            }

            case ENTITY -> {
                return broom_getPickEntityId(
                        pickedId,
                        this.field_2823.entity
                );
            }
        }

        return pickedId;
    }

    @Unique
    private final Int2IntMap broom_pickBlockLookupMap = Util.make(new Int2IntOpenHashMap(), map -> {
        map.put(Block.GRASS_BLOCK.id, Block.GRASS_BLOCK.id);
        map.put(Block.REDSTONE_WIRE.id, Item.REDSTONE.id);
        map.put(Block.REPEATER.id, Item.REPEATER.id);
        map.put(Block.POWERED_REPEATER.id, Item.REPEATER.id);
        map.put(Block.DOOR.id, Item.WOODEN_DOOR.id);
        map.put(Block.IRON_DOOR.id, Item.IRON_DOOR.id);
        map.put(Block.SIGN.id, Item.SIGN.id);
        map.put(Block.WALL_SIGN.id, Item.SIGN.id);
        map.put(Block.WHEAT.id, Item.SEEDS.id);
        map.put(Block.BED.id, Item.BED.id);
        map.put(Block.CAKE.id, Item.CAKE.id);
    });
}
