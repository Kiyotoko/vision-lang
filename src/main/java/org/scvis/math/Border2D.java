package org.scvis.math;

import java.io.Serializable;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * The Border2D interface represents a 2D border or shape in geometry. It provides methods for containment,
 * intersection, translation, rotation, and obtaining the centroid.
 *
 * @author karlz
 */
public interface Border2D<B extends Border2D<B>> extends Serializable {
    /**
     * Checks if the specified point is contained within the border.
     *
     * @param point2D the 2D vector representing the point
     * @return true if the point is contained within the border, false otherwise
     */
    @CheckReturnValue
    boolean contains(@Nonnull Point2D point2D);

    /**
     * Checks if the border intersects with the specified border.
     *
     * @param border2D the 2D border to check intersection with
     * @return true if the borders intersect, false otherwise
     */
    @CheckReturnValue
    boolean intersects(@Nonnull B border2D);

    /**
     * Translates the border by the specified translation values.
     *
     * @param x the translation along the x-axis
     * @param y the translation along the y-axis
     * @return a new Border2D object representing the translated border
     */
    @CheckReturnValue
    @Nonnull
    B translate(double x, double y);

    /**
     * Translates the border by the specified 2D vector.
     *
     * @param v the translation vector
     * @return a new Border2D object representing the translated border
     */
    @CheckReturnValue
    @Nonnull
    default B translate(@Nonnull Point2D v) {
        return translate(v.getX(), v.getY());
    }

    /**
     * Rotates the border around the specified center point by the specified angle in radians.
     *
     * @param center the center point of rotation
     * @param a      the angle of rotation in radians
     * @return a new Border2D object representing the rotated border
     */
    @CheckReturnValue
    @Nonnull
    B rotate(@Nonnull Point2D center, double a);

    /**
     * Rotates the border around its centroid by the specified angle in radians.
     *
     * @param a the angle of rotation in radians
     * @return a new Border2D object representing the rotated border
     */
    @CheckReturnValue
    @Nonnull
    default B rotate(double a) {
        return rotate(centroid(), a);
    }

    /**
     * Calculates and retrieves the centroid of the border.
     *
     * @return the centroid of the border as a Vector2D
     */
    @CheckReturnValue
    @Nonnull
    Point2D centroid();
}
