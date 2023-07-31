package civitas.celestis.math.quaternion;

import civitas.celestis.math.Numbers;
import civitas.celestis.math.rotation.Rotation;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.math.vector.Vector4;
import jakarta.annotation.Nonnull;

import java.util.Arrays;

/**
 * <h2>Quaternion</h2>
 * <p>Quaternions are used to represent the rotation of 3D vectors.</p>
 */
public class Quaternion extends Vector4 {
    //
    // Constants
    //

    /**
     * The identity quaternion.
     */
    public static final Quaternion IDENTITY = new Quaternion(1, 0, 0, 0);

    //
    // Constructors
    //

    /**
     * Creates a new quaternion.
     *
     * @param w W value of this quaternion
     * @param x X value of this quaternion
     * @param y Y value of this quaternion
     * @param z Z value of this quaternion
     */
    public Quaternion(double w, double x, double y, double z) {
        super(w, x, y, z);
    }

    /**
     * Creates a new quaternion.
     *
     * @param w Scalar part of this quaternion
     * @param v Vector part of this quaternion
     */
    public Quaternion(double w, @Nonnull Vector3 v) {
        super(w, v.x(), v.y(), v.z());
    }

    /**
     * Creates a quaternion from a {@link Vector4}.
     *
     * @param other Vector to use
     */
    public Quaternion(@Nonnull Vector4 other) {
        super(other);
    }

    //
    // Getters
    //

    /**
     * Gets the vector part of this quaternion.
     *
     * @return Vector part
     */
    @Nonnull
    public Vector3 vector() {
        return new Vector3(x(), y(), z());
    }

    //
    // Quaternion-Scalar Arithmetic
    //

    @Nonnull
    @Override
    public Quaternion add(double s) {
        return new Quaternion(super.add(s));
    }

    @Nonnull
    @Override
    public Quaternion subtract(double s) {
        return new Quaternion(super.subtract(s));
    }

    @Nonnull
    @Override
    public Quaternion multiply(double s) {
        return new Quaternion(super.multiply(s));
    }

    @Nonnull
    @Override
    public Quaternion divide(double s) throws ArithmeticException {
        return new Quaternion(super.divide(s));
    }

    //
    // Quaternion-Vector4 Arithmetic
    //

    @Nonnull
    @Override
    public Quaternion add(@Nonnull Vector4 v) {
        return new Quaternion(super.add(v));
    }

    @Nonnull
    @Override
    public Quaternion subtract(@Nonnull Vector4 v) {
        return new Quaternion(super.subtract(v));
    }

    //
    // Quaternion-Quaternion Arithmetic
    //

    /**
     * Multiplies this quaternion by another quaternion. (left-multiplication)
     *
     * @param q Quaternion to multiply with
     * @return Resulting quaternion
     */
    @Nonnull
    public Quaternion multiply(@Nonnull Quaternion q) {
        return new Quaternion(
                (w() * q.w()) - vector().dot(q.vector()),
                q.vector().multiply(w()).add(vector().multiply(q.w())).add(q.vector().cross(vector()))
        );
    }

    //
    // Util
    //

    /**
     * Scales the rotation this quaternion represents.
     *
     * @param s Scalar to scale rotation to
     * @return Scaled quaternion
     */
    @Nonnull
    public Quaternion scale(double s) {
        // No need to scale identity quaternions
        if (w() == 1) return IDENTITY;

        final double acos = Math.acos(w());
        return new Quaternion(
                Math.cos(acos * s),
                vector().divide(Math.sin(acos)).multiply(Math.sin(acos * s))
        );
    }

    /**
     * Gets the conjugate of this quaternion.
     *
     * @return Conjugate
     */
    @Nonnull
    public Quaternion conjugate() {
        return new Quaternion(w(), -x(), -y(), -z());
    }

    /**
     * Gets the inverse of this quaternion.
     *
     * @return Inverse
     */
    @Nonnull
    public Quaternion inverse() {
        return conjugate().multiply(Numbers.isqrt(magnitude2()));
    }

    /**
     * Assuming this is a rotation quaternion, this converts {@code this} to axis/angle notation.
     *
     * @return Rotation derived from {@code this}
     */
    @Nonnull
    public Rotation rotation() {
        final double angle = 2 * Math.acos(w());
        if (angle == 0) return Rotation.NO_ROTATION;

        final Vector3 axis = vector().divide(angle / 2);
        return new Rotation(axis, angle);
    }

    //
    // Serialization
    //

    /**
     * Deserializes a string to a quaternion.
     *
     * @param s String to deserialize
     * @return Deserialized quaternion
     * @throws NumberFormatException When the string is not parsable to a quaternion
     */
    @Nonnull
    public static Vector4 parseQuaternion(@Nonnull String s) throws NumberFormatException {
        if (!s.startsWith("Quaternion{")) throw new NumberFormatException("Given string is not a quaternion.");

        final String[] strings = s
                .replaceAll("Quaternion\\{", "")
                .replaceAll("}", "")
                .split(", ");

        final double[] values = {Double.NaN, Double.NaN, Double.NaN, Double.NaN};

        Arrays.stream(strings).forEach(string -> {
            final String[] split = string.split("=");
            if (split.length != 2) throw new NumberFormatException("Given string is not a quaternion.");

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
     * Serializes this quaternion to a string.
     *
     * @return Stringified vector
     */
    @Override
    @Nonnull
    public String toString() {
        return "Quaternion{" +
                "w=" + w() +
                ", x=" + x() +
                ", y=" + y() +
                ", z=" + z() +
                '}';
    }

}
