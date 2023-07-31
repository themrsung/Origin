package civitas.celestis.math.vector;

import civitas.celestis.math.Numbers;
import jakarta.annotation.Nonnull;

import java.util.Arrays;

/**
 * <h2>Vector3</h2>
 * <p>A two-dimensional vector.</p>
 */
public final class Vector2 implements Vector {
    //
    // Constants
    //

    /**
     * Absolute zero. Represents origin.
     */
    public static final Vector2 ZERO = new Vector2(0, 0);

    public static final Vector2 POSITIVE_X = new Vector2(1, 0);
    public static final Vector2 POSITIVE_Y = new Vector2(0, 1);
    public static final Vector2 NEGATIVE_X = new Vector2(-1, 0);
    public static final Vector2 NEGATIVE_Y = new Vector2(0, -1);

    //
    // Constructors
    //

    /**
     * Creates a new vector.
     *
     * @param x X value of this vector
     * @param y Y value of this vector
     */
    public Vector2(double x, double y) {
        this.x = Numbers.requireFinite(x);
        this.y = Numbers.requireFinite(y);
    }

    /**
     * Creates a new vector from an existing vector.
     *
     * @param other Vector to copy
     */
    public Vector2(@Nonnull Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    //
    // Variables
    //

    private final double x;
    private final double y;

    //
    // Getters
    //

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

    @Override
    public double magnitude() {
        final double isqrt = Numbers.isqrt(magnitude2());
        if (isqrt == 0) return 0;

        return 1 / isqrt;
    }

    @Override
    public double magnitude2() {
        return x * x + y * y;
    }

    //
    // Vector-Scalar Arithmetic
    //

    @Nonnull
    @Override
    public Vector2 add(double s) {
        return new Vector2(x + s, y + s);
    }

    @Nonnull
    @Override
    public Vector2 subtract(double s) {
        return new Vector2(x - s, y - s);
    }

    @Nonnull
    @Override
    public Vector2 multiply(double s) {
        return new Vector2(x * s, y * s);
    }

    @Nonnull
    @Override
    public Vector2 divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector2(x / s, y / s);
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
    public Vector2 add(@Nonnull Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param v Vector to subtract
     * @return Resulting vector
     */
    @Nonnull
    public Vector2 subtract(@Nonnull Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    /**
     * Gets the dot product of {@code this} and {@code v}.
     *
     * @param v Vector to multiply with
     * @return Dot product of two vectors
     */
    public double dot(@Nonnull Vector2 v) {
        return x * v.x + y * v.y;
    }

    /**
     * Multiplies this vector by another vector.
     *
     * @param v Vector to multiply with
     * @return Product of two vectors
     */
    @Nonnull
    public Vector2 multiply(@Nonnull Vector2 v) {
        return new Vector2(x * v.x - y * v.y, x * v.y + y * v.x);
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
        if (!(obj instanceof Vector2 v2)) return false;
        return x == v2.x && y == v2.y;
    }


    //
    // Util
    //

    @Nonnull
    @Override
    public Vector2 negate() {
        return multiply(-1);
    }

    @Nonnull
    @Override
    public Vector2 normalize() {
        return multiply(Numbers.isqrt(magnitude2()));
    }

    /**
     * Gets the distance between {@code this} and {@code v}.
     *
     * @param v Vector to get distance to
     * @return Distance between two vectors
     */
    public double distance(@Nonnull Vector2 v) {
        return subtract(v).magnitude();
    }

    /**
     * Gets the squared distance between {@code this} and {@code v}.
     *
     * @param v Vector to get distance to
     * @return Distance between two vectors
     */
    public double distance2(@Nonnull Vector2 v) {
        return subtract(v).magnitude2();
    }

    /**
     * Rotates this vector counter-clockwise by given angle.
     *
     * @param angle Angle in radians
     * @return Rotated vector
     */
    @Nonnull
    public Vector2 rotate(double angle) {
        return multiply(new Vector2(Math.cos(angle), Math.sin(angle)));
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
    public static Vector2 parseVector(@Nonnull String s) throws NumberFormatException {
        if (!s.startsWith("Vector2{")) throw new NumberFormatException("Given string is not a vector.");

        final String[] strings = s
                .replaceAll("Vector2\\{", "")
                .replaceAll("}", "")
                .split(", ");

        final double[] values = {Double.NaN, Double.NaN};

        Arrays.stream(strings).forEach(string -> {
            final String[] split = string.split("=");
            if (split.length != 2) throw new NumberFormatException("Given string is not a vector.");

            switch (split[0]) {
                case "x" -> values[0] = Double.parseDouble(split[1]);
                case "y" -> values[1] = Double.parseDouble(split[1]);
            }
        });

        return new Vector2(values[0], values[1]);
    }

    /**
     * Serializes this vector to a string.
     *
     * @return Stringified vector
     */
    @Override
    @Nonnull
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
