package me.huedev.broom.util;

import me.huedev.broom.block.BroomBlockProperties;
import me.huedev.broom.block.BroomSlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.impl.world.chunk.ChunkSection;
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk;

/**
 * @author paulevsGitch
 */
public class WorldHelper {
    public static HitResult raycast(World world, PlayerEntity player) {
        double dist = 5.0;
        float toRadians = (float) Math.PI / 180;
        float pitch = player.prevPitch + (player.pitch - player.prevPitch);

        double x = player.prevX + (player.x - player.prevX);
        double y = player.prevY + (player.y - player.prevY) + 1.62 - (double) player.eyeHeight;
        double z = player.prevZ + (player.z - player.prevZ);
        Vec3d pos = Vec3d.createCached(x, y, z);

        float yaw = player.prevYaw + (player.yaw - player.prevYaw);
        yaw = -yaw * toRadians - (float) Math.PI;
        float cosYaw = MathHelper.cos(yaw);
        float sinYaw = MathHelper.sin(yaw);
        float cosPitch = -MathHelper.cos(-pitch * toRadians);

        Vec3d dir = pos.add(
                sinYaw * cosPitch * dist,
                (MathHelper.sin(-pitch * ((float) Math.PI / 180))) * dist,
                cosYaw * cosPitch * dist
        );

        return world.method_161(pos, dir, false);
    }

    public static void setBlockSilent(World world, int x, int y, int z, BlockState state) {
        FlattenedChunk chunk = (FlattenedChunk) world.method_214(x >> 4, z >> 4);
        int index = world.getSectionIndex(y);
        ChunkSection section = chunk.sections[index];
        if (section == null) {
            section = new ChunkSection(index);
            chunk.sections[index] = section;
        }
        section.setBlockState(x & 15, y & 15, z & 15, state);
    }

    public static boolean isBlockStateFloorSupport(World world, int x, int y, int z) {
        BlockState state = world.getBlockState(x, y, z);
        if (state.getBlock() instanceof StairsBlock || state.getBlock() instanceof BroomSlabBlock) {
            BroomBlockProperties.TopBottom topBottom = state.get(BroomBlockProperties.TOP_BOTTOM);
            return topBottom == BroomBlockProperties.TopBottom.TOP;
        }
        return false;
    }
}
