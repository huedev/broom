package net.huedev.broom.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class BroomWetSpongeBlock extends TemplateBlock {
    public BroomWetSpongeBlock(Identifier id, Material material) {
        super(id, material);
        setTranslationKey(id);
        setHardness(SPONGE.getHardness());
        setSoundGroup(DIRT_SOUND_GROUP);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        if (world.dimension.field_2176) {
            world.setBlockStateWithNotify(x, y, z, Block.SPONGE.getDefaultState());
            world.method_173(null, 1011, x, y, z, 0);

            for(int var5 = 0; var5 < 8; ++var5) {
                world.addParticle("largesmoke", (double)x + Math.random(), (double)y + 1.2, (double)z + Math.random(), 0.0, 0.0, 0.0);
            }
        }
    }
}
