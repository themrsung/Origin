package civitas.celestis.math.unit;

import jakarta.annotation.Nonnull;

/**
 * <h2>MassUnit</h2>
 * <p>Contains mass conversion utilities.</p>
 */
public enum MassUnit {
    MICROGRAM(1e+9),
    MILLIGRAM(1000000),
    GRAIN(15432.358),
    GRAM(1000),
    POUND(2.20462),
    KILOGRAM(1),
    TON(0.001),

    /**
     * 1 tonne = 2000lbs
     */
    IMPERIAL_TON(0.000984207);

    /**
     * Creates a new unit of mass.
     *
     * @param scale Scale relative to kilograms (1 kg = x of unit)
     */
    MassUnit(double scale) {
        this.scale = scale;
    }

    private final double scale;

    /**
     * Converts a mass unit to {@code this}.
     *
     * @param sourceUnit Source unit
     * @param value      Value
     * @return Converted value
     */
    public double convert(@Nonnull MassUnit sourceUnit, double value) {
        return value / sourceUnit.scale * this.scale;
    }

    /**
     * Converts this unit to kilograms.
     *
     * @param value Value to convert
     * @return Value in kilograms
     */
    public double toKilograms(double value) {
        return value / this.scale;
    }
}
