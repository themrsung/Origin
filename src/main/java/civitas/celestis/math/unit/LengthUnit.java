package civitas.celestis.math.unit;

import jakarta.annotation.Nonnull;

/**
 * <h2>LengthUnit</h2>
 * <p>Contains length conversion utilities.</p>
 */
public enum LengthUnit {
    MICROMETER(1000000),
    MILLIMETER(1000),
    CENTIMETER(100),
    INCH(39.3701),
    YARD(1.09361),
    METER(1),
    KILOMETER(0.001),
    LIGHT_YEAR(1.057e-13);

    /**
     * Creates a new unit of length.
     *
     * @param scale Scale relative to meters (1 meter = x of unit)
     */
    LengthUnit(double scale) {
        this.scale = scale;
    }

    private final double scale;

    /**
     * Converts a length unit to {@code this}.
     *
     * @param sourceUnit Source unit
     * @param value      Value
     * @return Converted value
     */
    public double convert(@Nonnull LengthUnit sourceUnit, double value) {
        return value / sourceUnit.scale * this.scale;
    }

    /**
     * Converts this unit to meters.
     *
     * @param value Value to convert
     * @return Value in meters
     */
    public double toMeters(double value) {
        return value / this.scale;
    }
}
