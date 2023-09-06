package io.scvis.math;

import java.io.Serializable;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * The Border3D interface represents a 3D border or shape in geometry. It
 * provides methods for containment, intersection, translation, rotation, and
 * obtaining the centroid.
 * 
 * @author karlz
 */
public interface Border3D extends Serializable {
	/**
	 * Checks if the specified point is contained within the border.
	 *
	 * @param point3D the 3D vector representing the point
	 * @return true if the point is contained within the border, false otherwise
	 */
	@CheckReturnValue
	boolean contains(@Nonnull Point3D point3D);

	/**
	 * Checks if the border intersects with the specified border.
	 *
	 * @param border3D the 3D border to check intersection with
	 * @return true if the borders intersect, false otherwise
	 */
	@CheckReturnValue
	boolean intersects(@Nonnull Border3D border3D);

	/**
	 * Translates the border by the specified translation values along the x, y, and
	 * z axes.
	 *
	 * @param x the translation along the x-axis
	 * @param y the translation along the y-axis
	 * @param z the translation along the z-axis
	 * @return a new Border3D object representing the translated border
	 */
	@CheckReturnValue
	@Nonnull
	Border3D translate(double x, double y, double z);

	/**
	 * Translates the border by the specified 3D vector.
	 *
	 * @param v the translation vector
	 * @return a new Border3D object representing the translated border
	 */
	@CheckReturnValue
	@Nonnull
	default Border3D translate(@Nonnull Point3D v) {
		return translate(v.getX(), v.getY(), v.getZ());
	}

	/**
	 * Rotates the border around the specified center point by the specified angles
	 * in radians.
	 *
	 * @param center the center point of rotation
	 * @param a      the angle of rotation around the x-axis in radians
	 * @param b      the angle of rotation around the y-axis in radians
	 * @return a new Border3D object representing the rotated border
	 */
	@CheckReturnValue
	@Nonnull
	Border3D rotate(@Nonnull Point3D center, double a, double b);

	/**
	 * Rotates the border around its centroid by the specified angles in radians.
	 *
	 * @param a the angle of rotation around the x-axis in radians
	 * @param b the angle of rotation around the y-axis in radians
	 * @return a new Border3D object representing the rotated border
	 */
	@CheckReturnValue
	@Nonnull
	default Border3D rotate(double a, double b) {
		return rotate(centroid(), a, b);
	}

	/**
	 * Calculates and retrieves the centroid of the border.
	 *
	 * @return the centroid of the border as a Point3D
	 */
	@CheckReturnValue
	@Nonnull
    Point3D centroid();
}
