package org.scvis.math;

import java.io.Serializable;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * The Point3D class represents a 3-dimensional vector in geometry.
 * 
 * @author karlz
 */
@Immutable
public class Point3D implements Serializable {

	private static final long serialVersionUID = -4200231978633862588L;

	public static final @Nonnull Point3D ZERO = new Point3D(0.0, 0.0, 0.0);

	/**
	 * The x coordinate of the vector.}
	 */
	private final double x;

	/**
	 * The y coordinate of the vector.}
	 */
	private final double y;

	/**
	 * The z coordinate of the vector.}
	 */
	private final double z;

	/**
	 * Creates a Point3D object with the given x, y and z coordinates.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Adds the components of another vector to this vector and returns a new vector
	 * as the result.
	 *
	 * @param v the vector to add
	 * @return a new vector representing the sum of this vector and the given vector
	 */
	@Nonnull
	@CheckReturnValue
	public Point3D add(@Nonnull Point3D v) {
		return add(v.getX(), v.getY(), v.getZ());
	}

	/**
	 * Adds the specified coordinates to this vector and returns a new vector as the
	 * result.
	 *
	 * @param x the x-coordinate to add
	 * @param y the y-coordinate to add
	 * @param z the z-coordinate to add
	 * @return a new vector representing the sum of this vector and the specified
	 *         coordinates
	 */
	@Nonnull
	@CheckReturnValue
	public Point3D add(double x, double y, double z) {
		return new Point3D(this.x + x, this.y + y, this.z + z);
	}

	/**
	 * Subtracts the components of another vector from this vector and returns a new
	 * vector as the result.
	 *
	 * @param v the vector to subtract
	 * @return a new vector representing the difference between this vector and the
	 *         given vector
	 */
	@Nonnull
	@CheckReturnValue
	public Point3D subtract(@Nonnull Point3D v) {
		return subtract(v.getX(), v.getY(), v.getZ());
	}

	/**
	 * Subtracts the specified coordinates from this vector and returns a new vector
	 * as the result.
	 *
	 * @param x the x-coordinate to subtract
	 * @param y the y-coordinate to subtract
	 * @param z the z-coordinate to subtract
	 * @return a new vector representing the difference between this vector and the
	 *         specified coordinates
	 */
	@Nonnull
	@CheckReturnValue
	public Point3D subtract(double x, double y, double z) {
		return new Point3D(this.x - x, this.y - y, this.z - z);
	}

	/**
	 * Multiplies this vector by a scalar value and returns a new vector as the
	 * result.
	 *
	 * @param s the scalar value to multiply by
	 * @return a new vector representing the product of this vector and the scalar
	 *         value
	 */
	@Nonnull
	@CheckReturnValue
	public Point3D multiply(double s) {
		return new Point3D(x * s, y * s, z * s);
	}

	/**
	 * Calculates the distance between this vector and another vector.
	 *
	 * @param v the vector to calculate the distance to
	 * @return the distance between this vector and the given vector
	 */
	@CheckReturnValue
	public double distance(@Nonnull Point3D v) {
		return distance(v.x, v.y, v.z);
	}

	/**
	 * Calculates the distance between this vector and the specified coordinates.
	 *
	 * @param x the x-coordinate of the vector
	 * @param y the y-coordinate of the vector
	 * @param z the z-coordinate of the vector
	 * @return the distance between this vector and the specified coordinates
	 */
	@CheckReturnValue
	public double distance(double x, double y, double z) {
		double dx = this.x - x;
		double dy = this.x - y;
		double dz = this.z - z;
		return Math.sqrt(Math.sqrt(dx * dx + dy * dy) + dz * dz);
	}

	/**
	 * Returns a normalized version of this vector. If the vector has a magnitude of
	 * 0, the zero vector is returned.
	 *
	 * @return a new vector representing the normalized version of this vector
	 */
	@Nonnull
	@CheckReturnValue
	public Point3D normalize() {
		double mag = magnitude();
		if (mag == 0.0) {
			return ZERO;
		}
		return new Point3D(x / mag, y / mag, z / mag);
	}

	/**
	 * Calculates the midpoint between this vector and another vector.
	 *
	 * @param v the vector to calculate the midpoint with
	 * @return a new vector representing the midpoint between this vector and the
	 *         given vector
	 */
	@Nonnull
	@CheckReturnValue
	public Point3D midpoint(@Nonnull Point3D v) {
		return midpoint(v.x, v.y, v.z);
	}

	/**
	 * Calculates the midpoint between this vector and the specified coordinates.
	 *
	 * @param x the x-coordinate of the vector
	 * @param y the y-coordinate of the vector
	 * @param z the z-coordinate of the vector
	 * @return a new vector representing the midpoint between this vector and the
	 *         specified coordinates
	 */
	@Nonnull
	@CheckReturnValue
	public Point3D midpoint(double x, double y, double z) {
		return new Point3D(x + (this.x - x) / 2.0, y + (this.y - y) / 2.0, z + (this.z - z) / 2);
	}

	/**
	 * Calculates the magnitude (length) of this vector.
	 *
	 * @return the magnitude of this vector
	 */
	public double magnitude() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns the x-coordinate of this vector.
	 *
	 * @return the x-coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y-coordinate of this vector.
	 *
	 * @return the y-coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns the z-coordinate of this vector.
	 *
	 * @return the z-coordinate
	 */
	public double getZ() {
		return z;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Point3D))
			return false;
		if (obj == this)
			return true;
		Point3D vec = (Point3D) obj;
		return x == vec.x && y == vec.y && z == vec.z;
	}

	@Nonnull
	@CheckReturnValue
	public Vector toVector() {
		return new Vector(new double[]{x, y, z});
	}

	private transient int hash;

	@Override
	public int hashCode() {
		if (hash == 0)
			hash = super.hashCode();
		return hash;
	}

	@Override
	public String toString() {
		return "Point3D [x = " + x + ", y = " + y + ", z = " + z + "]";
	}
}
