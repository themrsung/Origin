package civitas.celestis.math.vector;

import civitas.celestis.math.Numbers;
import civitas.celestis.math.quaternion.Quaternion;
import civitas.celestis.math.rotation.Rotation;
import jakarta.annotation.Nonnull;

import java.util.Arrays;

/**
 * <h2>Vector3</h2>
 * <p>A three-dimensional vector.</p>
 */
public final class Vector3 implements Vector {
    //
    // Constants
    //

    /**
     * Absolute zero. Represents origin.
     */
    public static final Vector3 ZERO = new Vector3(0, 0, 0);

    public static final Vector3 POSITIVE_X = new Vector3(1, 0, 0);
    public static final Vector3 POSITIVE_Y = new Vector3(0, 1, 0);
    public static final Vector3 POSITIVE_Z = new Vector3(0, 0, 1);
    public static final Vector3 NEGATIVE_X = new Vector3(-1, 0, 0);
    public static final Vector3 NEGATIVE_Y = new Vector3(0, -1, 0);
    public static final Vector3 NEGATIVE_Z = new Vector3(0, 0, -1);

    //
    // Constructors
    //

    /**
     * Creates a new vector.
     *
     * @param x X value of this vector
     * @param y Y value of this vector
     * @param z Z value of this vector
     */
    public Vector3(double x, double y, double z) {
        this.x = Numbers.requireFinite(x);
        this.y = Numbers.requireFinite(y);
        this.z = Numbers.requireFinite(z);
    }

    /**
     * Creates a new vector from an existing vector.
     *
     * @param other Vector to copy
     */
    public Vector3(@Nonnull Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    //
    // Variables
    //

    private final double x;
    private final double y;
    private final double z;

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
        return x * x + y * y + z * z;
    }

    //
    // Vector-Scalar Arithmetic
    //

    @Nonnull
    @Override
    public Vector3 add(double s) {
        return new Vector3(x + s, y + s, z + s);
    }

    @Nonnull
    @Override
    public Vector3 subtract(double s) {
        return new Vector3(x - s, y - s, z - s);
    }

    @Nonnull
    @Override
    public Vector3 multiply(double s) {
        return new Vector3(x * s, y * s, z * s);
    }

    @Nonnull
    @Override
    public Vector3 divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Vector3(x / s, y / s, z / s);
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
    public Vector3 add(@Nonnull Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param v Vector to subtract
     * @return Resulting vector
     */
    @Nonnull
    public Vector3 subtract(@Nonnull Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Gets the dot product of {@code this} and {@code v}.
     *
     * @param v Vector to multiply with
     * @return Dot product of two vectors
     */
    public double dot(@Nonnull Vector3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Gets the cross product of {@code this} and {@code v}.
     *
     * @param v Vector to multiply with
     * @return Cross product of two vectors
     */
    @Nonnull
    public Vector3 cross(@Nonnull Vector3 v) {
        return new Vector3(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
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
        if (!(obj instanceof Vector3 v3)) return false;
        return x == v3.x && y == v3.y && z == v3.z;
    }


    //
    // Util
    //

    @Nonnull
    @Override
    public Vector3 negate() {
        return multiply(-1);
    }

    @Nonnull
    @Override
    public Vector3 normalize() {
        return multiply(Numbers.isqrt(magnitude2()));
    }

    /**
     * Gets the distance between {@code this} and {@code v}.
     *
     * @param v Vector to get distance to
     * @return Distance between two vectors
     */
    public double distance(@Nonnull Vector3 v) {
        return subtract(v).magnitude();
    }

    /**
     * Gets the squared distance between {@code this} and {@code v}.
     *
     * @param v Vector to get distance to
     * @return Distance between two vectors
     */
    public double distance2(@Nonnull Vector3 v) {
        return subtract(v).magnitude2();
    }

    /**
     * Rotates this vector by a rotation.
     *
     * @param r Rotation to rotate by
     * @return Rotated vector
     */
    @Nonnull
    public Vector3 rotate(@Nonnull Rotation r) {
        return rotate(r.quaternion());
    }

    /**
     * Rotates this vector by a rotation quaternion.
     *
     * @param rq Rotation quaternion to rotate by
     * @return Rotated vector
     */
    @Nonnull
    public Vector3 rotate(@Nonnull Quaternion rq) {
        return rq.multiply(quaternion()).multiply(rq.conjugate()).vector();
    }

    /**
     * Converts this vector to a pure quaternion.
     *
     * @return Pure quaternion of {@code this}
     */
    @Nonnull
    public Quaternion quaternion() {
        return new Quaternion(0, x, y, z);
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
    public static Vector3 parseVector(@Nonnull String s) throws NumberFormatException {
        if (!s.startsWith("Vector3{")) throw new NumberFormatException("Given string is not a vector.");

        final String[] strings = s
                .replaceAll("Vector3\\{", "")
                .replaceAll("}", "")
                .split(", ");

        final double[] values = {Double.NaN, Double.NaN, Double.NaN};

        Arrays.stream(strings).forEach(string -> {
            final String[] split = string.split("=");
            if (split.length != 2) throw new NumberFormatException("Given string is not a vector.");

            switch (split[0]) {
                case "x" -> values[0] = Double.parseDouble(split[1]);
                case "y" -> values[1] = Double.parseDouble(split[1]);
                case "z" -> values[2] = Double.parseDouble(split[1]);
            }
        });

        return new Vector3(values[0], values[1], values[2]);
    }

    /**
     * Serializes this vector to a string.
     *
     * @return Stringified vector
     */
    @Override
    @Nonnull
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
