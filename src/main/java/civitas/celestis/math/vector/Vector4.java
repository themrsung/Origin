package civitas.celestis.math.vector;

import civitas.celestis.math.Numbers;
import jakarta.annotation.Nonnull;

import java.util.Arrays;

/**
 * <h2>Vector4</h2>
 * <p>A four-dimensional vector.</p>
 */
public class Vector4 implements Vector {
    //
    // Constants
    //

    /**
     * Absolute zero. Represents origin.
     */
    public static final Vector4 ZERO = new Vector4(0, 0, 0, 0);

    public static final Vector4 POSITIVE_W = new Vector4(1, 0, 0, 0);
    public static final Vector4 POSITIVE_X = new Vector4(0, 1, 0, 0);
    public static final Vector4 POSITIVE_Y = new Vector4(0, 0, 1, 0);
    public static final Vector4 POSITIVE_Z = new Vector4(0, 0, 0, 1);
    public static final Vector4 NEGATIVE_W = new Vector4(-1, 0, 0, 0);
    public static final Vector4 NEGATIVE_X = new Vector4(0, -1, 0, 0);
    public static final Vector4 NEGATIVE_Y = new Vector4(0, 0, -1, 0);
    public static final Vector4 NEGATIVE_Z = new Vector4(0, 0, 0, -1);

    //
    // Constructors
    //

    /**
     * Creates a new vector.
     *
     * @param w W value of this vector
     * @param x X value of this vector
     * @param y Y value of this vector
     * @param z Z value of this vector
     */
    public Vector4(double w, double x, double y, double z) {
        this.w = Numbers.requireFinite(w);
        this.x = Numbers.requireFinite(x);
        this.y = Numbers.requireFinite(y);
        this.z = Numbers.requireFinite(z);
    }

    /**
     * Creates a vector from an existing vector.
     *
     * @param other Vector to copy
     */
    public Vector4(@Nonnull Vector4 other) {
        this.w = other.w;
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    //
    // Variables
    //

    private final double w;
    private final double x;
    private final double y;
    private final double z;

    //
    // Getters
    //

    /**
     * Gets the W value of this vector.
     *
     * @return W value
     */
    public double w() {return w;}

    /**
     * Gets the X value of this vector.
     *
     * @return X value
     */
    public double x() {return x;}

    /**
     * Gets the Y value of this vector.
     *
     * @return Y value
     */
    public double y() {return y;}

    /**
     * Gets the Z value of this vector.
     *
     * @return Z value
     */
    public double z() {return z;}

    @Override
    public double magnitude() {
        final double isqrt = Numbers.isqrt(magnitude2());
        if (isqrt == 0) return 0;

        return 1 / isqrt;
    }

    @Override
    public double magnitude2() {
        return w * w + x * x + y * y + z * z;
    }

    //
    // Vector-Scalar Arithmetic
    //

    @Nonnull
    @Override
    public Vector4 add(double s) {
        return new Vector4(w + s, x + s, y + s, z + s);
    }

    @Nonnull
    @Override
    public Vector4 subtract(double s) {
        return new Vector4(w - s, x - s, y - s, z - s);
    }

    @Nonnull
    @Override
    public Vector4 multiply(double s) {
        return new Vector4(w * s, x * s, y * s, z * s);
    }

    @Nonnull
    @Override
    public Vector4 divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector4(w / s, x / s, y / s, z / s);
    }

    //
    // Vector-Vector Arithmetic
    //

    /**
     * Adds another vector to this vector.
     *
     * @param v Vector to add
     * @return Resulting vector
     */
    @Nonnull
    public Vector4 add(@Nonnull Vector4 v) {
        return new Vector4(w + v.w, x + v.x, y + v.y, z + v.z);
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param v Vector to subtract
     * @return Resulting vector
     */
    @Nonnull
    public Vector4 subtract(@Nonnull Vector4 v) {
        return new Vector4(w - v.w, x - v.x, y - v.y, z - v.y);
    }

    //
    // Equality
    //

    /**
     * Checks for equality.
     *
     * @param obj Object to compare to
     * @return {@code true} if the values are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Vector4 v4)) return false;
        return w == v4.w && x == v4.x && y == v4.y && z == v4.z;
    }

    //
    // Util
    //

    @Nonnull
    @Override
    public Vector4 negate() {
        return multiply(-1);
    }

    @Nonnull
    @Override
    public Vector4 normalize() {
        return multiply(Numbers.isqrt(magnitude2()));
    }

    /**
     * Gets the distance between {@code this} and {@code v}.
     *
     * @param v Vector to get distance to
     * @return Distance between two vectors
     */
    public double distance(@Nonnull Vector4 v) {
        return subtract(v).magnitude();
    }

    /**
     * Gets the squared distance between {@code this} and {@code v}.
     *
     * @param v Vector to get distance to
     * @return Distance between two vectors
     */
    public double distance2(@Nonnull Vector4 v) {
        return subtract(v).magnitude2();
    }

    //
    // Serialization
    //

    /**
     * Deserializes a string to a vector.
     *
     * @param s String to deserialize
     * @return Deserialized vector
     * @throws NumberFormatException When the string is not parsable to a vector
     */
    @Nonnull
    public static Vector4 parseVector(@Nonnull String s) throws NumberFormatException {
        if (!s.startsWith("Vector4{")) throw new NumberFormatException("Given string is not a vector.");

        final String[] strings = s
                .replaceAll("Vector4\\{", "")
                .replaceAll("}", "")
                .split(", ");

        final double[] values = {Double.NaN, Double.NaN, Double.NaN, Double.NaN};

        Arrays.stream(strings).forEach(string -> {
            final String[] split = string.split("=");
            if (split.length != 2) throw new NumberFormatException("Given string is not a vector.");

            switch (split[0]) {
                case "w" -> values[0] = Double.parseDouble(split[1]);
                case "x" -> values[1] = Double.parseDouble(split[1]);
                case "y" -> values[2] = Double.parseDouble(split[1]);
                case "z" -> values[3] = Double.parseDouble(split[1]);
            }
        });

        return new Vector4(values[0], values[1], values[2], values[3]);
    }

    /**
     * Serializes this vector to a string.
     *
     * @return Stringified vector
     */
    @Override
    @Nonnull
    public String toString() {
        return "Vector4{" +
                "w=" + w +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
