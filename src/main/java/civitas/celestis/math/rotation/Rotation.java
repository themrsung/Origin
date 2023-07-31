package civitas.celestis.math.rotation;

import civitas.celestis.math.quaternion.Quaternion;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.math.vector.Vector4;
import jakarta.annotation.Nonnull;

import java.util.Arrays;

/**
 * <h2>Rotation</h2>
 * <p>Represents a 3D rotation using axis/angle notation.</p>
 */
public class Rotation extends Vector4 {
    //
    // Constants
    //

    /**
     * Represents being perfectly upright with no rotation.
     */
    public static final Rotation NO_ROTATION = new Rotation(Vector3.POSITIVE_Y, 0);

    //
    // Constructors
    //

    /**
     * Creates a new rotation.
     * Angle obeys the right-hand rule.
     *
     * @param axis  Axis of rotation
     * @param angle Angle in radians
     */
    public Rotation(@Nonnull Vector3 axis, double angle) {
        super(angle, axis.x(), axis.y(), axis.z());
    }

    /**
     * Creates a new rotation.
     *
     * @param angle Angle in radians
     * @param x     X value of axis
     * @param y     Y value of axis
     * @param z     Z value of axis
     */
    public Rotation(double angle, double x, double y, double z) {
        super(angle, x, y, z);
    }

    /**
     * Creates a new rotation from a Vector4.
     *
     * @param other Vector to use
     */
    public Rotation(@Nonnull Vector4 other) {
        super(other);
    }

    //
    // Getters
    //

    /**
     * Gets the angle of this rotation.
     *
     * @return Angle in radians
     */
    public double angle() {
        return w();
    }

    /**
     * Gets the angle of this rotation.
     *
     * @return Angle in degrees
     */
    public double degrees() {
        return Math.toDegrees(w());
    }

    /**
     * Gets the axis of this rotation.
     *
     * @return Axis of rotation
     */
    @Nonnull
    public Vector3 axis() {
        return new Vector3(x(), y(), z()).normalize();
    }

    //
    // Util
    //

    /**
     * Scales this rotation by given scalar.
     *
     * @param s Scalar to scale by
     * @return Scaled rotation
     */
    @Nonnull
    public Rotation scale(double s) {
        return new Rotation(w() * s, x(), y(), z());
    }

    /**
     * Rotates this rotation by another rotation.
     *
     * @param r Rotation to rotate by
     * @return Resulting rotation
     */
    @Nonnull
    public Rotation rotate(@Nonnull Rotation r) {
        return rotate(r.quaternion());
    }

    /**
     * Rotates this rotation by a rotation quaternion.
     *
     * @param rq Rotation quaternion to rotate by
     * @return Resulting rotation
     */
    @Nonnull
    public Rotation rotate(@Nonnull Quaternion rq) {
        return rq.multiply(quaternion()).rotation();
    }

    /**
     * Converts this rotation to a rotation quaternion.
     *
     * @return Rotation quaternion derived from {@code this}
     */
    @Nonnull
    public Quaternion quaternion() {
        return new Quaternion(Math.cos(w() / 2), axis().normalize().multiply(Math.sin(w() / 2)));
    }

    //
    // Serialization
    //

    /**
     * Deserializes a string to a rotation.
     *
     * @param s String to deserialize
     * @return Deserialized rotation
     * @throws NumberFormatException When the string is not parsable to a rotation
     */
    @Nonnull
    public static Rotation parseRotation(@Nonnull String s) throws NumberFormatException {
        if (!s.startsWith("Rotation{")) throw new NumberFormatException("Given string is not a rotation.");

        final String[] strings = s
                .replaceAll("Rotation\\{", "")
                .replaceAll("}", "")
                .split(", ");

        final double[] values = {Double.NaN, Double.NaN, Double.NaN, Double.NaN};

        Arrays.stream(strings).forEach(string -> {
            final String[] split = string.split("=");
            if (split.length != 2) throw new NumberFormatException("Given string is not a rotation.");

            switch (split[0]) {
                case "angle" -> values[0] = Double.parseDouble(split[1]);
                case "x" -> values[1] = Double.parseDouble(split[1]);
                case "y" -> values[2] = Double.parseDouble(split[1]);
                case "z" -> values[3] = Double.parseDouble(split[1]);
            }
        });

        return new Rotation(new Vector3(values[1], values[2], values[3]), values[0]);
    }


    /**
     * Serializes this vector to a string.
     *
     * @return Stringified vector
     */
    @Nonnull
    @Override
    public String toString() {
        return "Rotation{" +
                "angle=" + w() +
                ", x=" + x() +
                ", y=" + y() +
                ", z=" + z() +
                '}';
    }
}
