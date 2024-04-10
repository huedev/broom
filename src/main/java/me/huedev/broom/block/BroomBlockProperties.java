package me.huedev.broom.block;

import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.util.StringIdentifiable;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;

/**
 * @author paulevsGitch
 */
public class BroomBlockProperties {
    public static final EnumProperty<Direction> FACING = EnumProperty.of("facing", Direction.class, dir -> dir.getAxis().isHorizontal());
    public static final EnumProperty<Direction> DIRECTION = EnumProperty.of("direction", Direction.class);
    public static final EnumProperty<ChestType> CHEST_TYPE = EnumProperty.of("type", ChestType.class);
    public static final EnumProperty<TopBottom> TOP_BOTTOM = EnumProperty.of("half", TopBottom.class);
    public static final EnumProperty<Axis> AXIS = EnumProperty.of("axis", Axis.class);
    public static final BooleanProperty NATURAL = BooleanProperty.of("natural");
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public static final BooleanProperty OPENED = BooleanProperty.of("opened");
    public static final IntProperty SAPLING_STAGE = IntProperty.of("stage", 0, 1);

    public enum ChestType implements StringIdentifiable {
        SINGLE("single"), LEFT("left"), RIGHT("right");

        final String name;

        ChestType(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

        @Override
        public String asString() {
            return name;
        }
    }

    public enum TopBottom implements StringIdentifiable {
        TOP("top"), BOTTOM("bottom");

        final String name;

        TopBottom(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

        @Override
        public String asString() {
            return name;
        }
    }
}
