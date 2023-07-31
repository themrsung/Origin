package civitas.celestis.math.vector;

import civitas.celestis.math.quaternion.Quaternion;
import civitas.celestis.math.rotation.Rotation;
import jakarta.annotation.Nonnull;

import java.io.Serializable;

/**
 * <h2>Vector</h2>
 * <p>A superinterface for all vectors.</p>
 */
public interface Vector extends Serializable {
    /**
     * Adds a scalar to this vector.
     *
     * @param s Scalar to add
     * @return Resulting vector
     */
    @Nonnull
    Vector add(double s);

    /**
     * Subtracts a scalar from this vector.
     *
     * @param s Scalar to subtract
     * @return Resulting vector
     */
    @Nonnull
    Vector subtract(double s);

    /**
     * Multiplies this vector by a scalar.
     *
     * @param s Scalar to multiply with
     * @return Resulting vector
     */
    @Nonnull
    Vector multiply(double s);

    /**
     * Divides this vector by a scalar.
     *
     * @param s Scalar to divide by
     * @return Resulting vector
     * @throws ArithmeticException When the denominator is zero
     */
    @Nonnull
    Vector divide(double s) throws ArithmeticException;

    /**
     * Gets the magnitude of this vector.
     *
     * @return Magnitude
     */
    double magnitude();

    /**
     * Gets the squared magnitude of this vector.
     *
     * @return Squared magnitude
     */
    double magnitude2();

    /**
     * Negates this vector.
     *
     * @return Negated vector
     */
    @Nonnull
    Vector negate();

    /**
     * Normalizes this vector to a unit vector.
     *
     * @return Normalized unit vector
     */
    @Nonnull
    Vector normalize();

    /**
     * Parses a string to a vector.
     *
     * @param s String to parse
     * @return Parsed vector
     * @throws NumberFormatException When the string is not parsable to a vector
     */
    @Nonnull
    static Vector parse(@Nonnull String s) throws NumberFormatException {
        try {
            return Vector2.parseVector(s);
        } catch (NumberFormatException e1) {
            try {
                return Vector3.parseVector(s);
            } catch (NumberFormatException e2) {
                try {
                    return Vector4.parseVector(s);
                } catch (NumberFormatException e3) {
                    try {
                        return Quaternion.parseQuaternion(s);
                    } catch (NumberFormatException e4) {
                        try {
                            return Rotation.parseRotation(s);
                        } catch (NumberFormatException e5) {
                            throw new NumberFormatException("String is not a vector.");
                        }
                    }
                }
            }
        }
    }
}
