package me.huedev.broom.block;

import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;

/**
 * @author paulevs
 */
public class BroomBlockProperties {
    public static final EnumProperty<Direction> FACING = EnumProperty.of("facing", Direction.class, dir -> dir.getAxis().isHorizontal());
    public static final EnumProperty<Direction> DIRECTION = EnumProperty.of("direction", Direction.class);
    public static final EnumProperty<Axis> AXIS = EnumProperty.of("axis", Axis.class);
    public static final BooleanProperty NATURAL = BooleanProperty.of("natural");
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
}
