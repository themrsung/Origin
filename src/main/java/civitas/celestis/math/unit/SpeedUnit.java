package civitas.celestis.math.unit;

import jakarta.annotation.Nonnull;

/**
 * <h2>SpeedUnit</h2>
 * <p>
 * Contains speed conversion utilities.
 * This can also be used to convert units of acceleration.
 * </p>
 */
public enum SpeedUnit {
    KILOMETERS_PER_HOUR(3.6),
    FEET_PER_SECOND(3.28084),
    MILES_PER_HOUR(2.23694),
    METERS_PER_SECOND(1),
    LIGHT_SPEED(3.33555704e-9);

    /**
     * Creates a new unit of speed.
     *
     * @param scale Scale relative to meters per second (1 m/s = x of unit)
     */
    SpeedUnit(double scale) {
        this.scale = scale;
    }

    private final double scale;

    /**
     * Converts a speed unit to {@code this}.
     *
     * @param sourceUnit Source unit
     * @param value      Value
     * @return Converted value
     */
    public double convert(@Nonnull SpeedUnit sourceUnit, double value) {
        return value / sourceUnit.scale * this.scale;
    }

    /**
     * Converts this unit to m/s.
     *
     * @param value Value to convert
     * @return Value in m/s
     */
    public double toMetersPerSecond(double value) {
        return value / this.scale;
    }
}
