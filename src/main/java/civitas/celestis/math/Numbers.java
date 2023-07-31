package civitas.celestis.math;

/**
 * <h2>Numbers</h2>
 * <p>A numerical utility class.</p>
 */
public final class Numbers {
    /**
     * Denotes explicitly that a given field requires a finite value.
     *
     * @param v Value to check
     * @return Value given as parameter
     */
    public static double requireFinite(double v) {
        if (!Double.isFinite(v)) throw new IllegalArgumentException("Given field requires a finite double.");

        return v;
    }

    /**
     * Gets the inverse square root of given number.
     *
     * @param x Number to inverse square root
     * @return Inverse square root
     */
    public static double isqrt(double x) {
        double result = x;
        double xhalf = 0.5d * result;

        long l = Double.doubleToLongBits(result);

        // Fast inverse square root
        l = 0x5fe6ec85e7de30daL - (l >> 1);

        result = Double.longBitsToDouble(l);

        // 4 iterations of Newton's method
        for (int i = 0; i < 4; i++) {
            result = result * (1.5d - xhalf * result * result);
        }

        return result;
    }
}
