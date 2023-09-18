package io.scvis.math;

import java.io.Serializable;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The Point2D class represents a 2-dimensional point in geometry.
 * 
 * @author karlz
 */
public class Point2D implements Serializable {

	private static final long serialVersionUID = -9019588241960612260L;

	public static final @Nonnull Point2D ZERO = new Point2D(0.0, 0.0);
	
	/**
	 * The x coordinate of the vector.
	 */
	private final double x;

	/**
	 * The y coordinate of the vector.
	 */
	private final double y;

	/**
	 * Creates a Vector2D object with the given x and y coordinates.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Adds the components of another vector to this vector and returns a new vector
	 * as the result.
	 *
	 * @param v the vector to add
	 * @return a new vector representing the sum of this vector and the given vector
	 */
	@CheckReturnValue
	@Nonnull
	public Point2D add(@Nonnull Point2D v) {
		return add(v.getX(), v.getY());
	}

	/**
	 * Adds the specified coordinates to this vector and returns a new vector as the
	 * result.
	 *
	 * @param x the x-coordinate to add
	 * @param y the y-coordinate to add
	 * @return a new vector representing the sum of this vector and the specified
	 *         coordinates
	 */
	@CheckReturnValue
	@Nonnull
	public Point2D add(double x, double y) {
		return new Point2D(this.x + x, this.y + y);
	}

	/**
	 * Subtracts the components of another vector from this vector and returns a new
	 * vector as the result.
	 *
	 * @param v the vector to subtract
	 * @return a new vector representing the difference between this vector and the
	 *         given vector
	 */
	@CheckReturnValue
	@Nonnull
	public Point2D subtract(@Nonnull Point2D v) {
		return subtract(v.getX(), v.getY());
	}

	/**
	 * Subtracts the given x and y coordinates from this vector and returns a new
	 * Vector2D object.
	 *
	 * @param x the x coordinate to subtract
	 * @param y the y coordinate to subtract
	 * @return a new Vector2D object representing the difference of the two vectors
	 */
	@CheckReturnValue
	@Nonnull
	public Point2D subtract(double x, double y) {
		return new Point2D(this.x - x, this.y - y);
	}

	/**
	 * Multiplies this vector by a scalar value and returns a new vector as the
	 * result.
	 *
	 * @param s the scalar value to multiply by
	 * @return a new vector representing the product of this vector and the scalar
	 *         value
	 */
	@CheckReturnValue
	@Nonnull
	public Point2D multiply(double s) {
		return new Point2D(x * s, y * s);
	}

	/**
	 * Calculates the dot product between this vector and another vector.
	 *
	 * @param v the vector to calculate the dot product with
	 * @return the dot product between this vector and the given vector
	 */
	@CheckReturnValue
	public double dotProduct(@Nonnull Point2D v) {
		return dotProduct(v.x, v.y);
	}

	/**
	 * Calculates the dot product between this vector and the specified coordinates.
	 *
	 * @param x the x-coordinate of the vector
	 * @param y the y-coordinate of the vector
	 * @return the dot product between this vector and the specified coordinates
	 */
	@CheckReturnValue
	public double dotProduct(double x, double y) {
		return this.x * x + this.y * y;
	}

	/**
	 * Calculates the distance between this vector and the given vector.
	 *
	 * @param v the vector to calculate the distance to
	 * @return the distance between the two vectors
	 */
	@CheckReturnValue
	public double distance(@Nonnull Point2D v) {
		return distance(v.x, v.y);
	}

	/**
	 * Calculates the distance between this vector and the vector with the given x
	 * and y coordinates.
	 *
	 * @param x the x coordinate of the other vector
	 * @param y the y coordinate of the other vector
	 * @return the distance between the two vectors
	 */
	public double distance(double x, double y) {
		double dx = this.x - x;
		double dy = this.y - y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * Returns a normalized version of this vector. If the vector is zero based, a
	 * zero vector is returned.
	 *
	 * @return a new vector representing the normalized version of this vector
	 */
	@CheckReturnValue
	@Nonnull
	public Point2D normalize() {
		double mag = magnitude();
		if (mag == 0.0) {
			return ZERO;
		}
		return new Point2D(x / mag, y / mag);
	}

	/**
	 * Calculates the midpoint between this vector and the specified coordinates.
	 *
	 * @param v the vector
	 * @return a new vector representing the midpoint between this vector and the
	 *         specified coordinates
	 */
	@CheckReturnValue
	@Nonnull
	public Point2D midpoint(@Nonnull Point2D v) {
		return midpoint(v.x, v.y);
	}

	/**
	 * Calculates the midpoint between this vector and the vector with the given x
	 * and y coordinates.
	 *
	 * @param x the x coordinate of the other vector
	 * @param y the y coordinate of the other vector
	 * @return the midpoint between the two vectors
	 */
	@CheckReturnValue
	@Nonnull
	public Point2D midpoint(double x, double y) {
		return new Point2D(x + (this.x - x) / 2.0,
				y + (this.y - y) / 2.0);
	}

	/**
	 * Calculates the angle between this vector and another vector.
	 *
	 * @param v the vector to calculate the angle to
	 * @return the angle between this vector and the given vector
	 */
	@CheckReturnValue
	public double angle(@Nonnull Point2D v) {
		return angle(v.getX(), v.getY());
	}

	/**
	 * Calculates the angle between this vector and the specified coordinates.
	 *
	 * @param x the x-coordinate of the vector
	 * @param y the y-coordinate of the vector
	 * @return the angle between this vector and the specified coordinates
	 */
	@CheckReturnValue
	public double angle(double x, double y) {
		return Math.atan2(this.y - y, this.x - x);
	}

	/**
	 * Rotates this vector by the specified angle and returns a new rotated vector.
	 *
	 * @param a the angle to rotate by, in radians
	 * @return a new vector representing the result of rotating this vector by the
	 *         specified angle
	 */
	@CheckReturnValue
	@Nonnull
	public Point2D rotate(double a) {
		return new Point2D(Math.cos(a) * x - Math.sin(a) * y, Math.sin(a) * x + Math.cos(a) * y);
	}

	/**
	 * Calculates the magnitude (length) of this vector.
	 *
	 * @return the magnitude of the vector
	 */
	@CheckReturnValue
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Returns the x coordinate of this vector.
	 *
	 * @return the x coordinate
	 */
	@CheckReturnValue
	public double getX() {
		return x;
	}

	/**
	 * Returns the x coordinate of this vector.
	 *
	 * @return the x coordinate
	 */
	@CheckReturnValue
	public double getY() {
		return y;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Point2D))
			return false;
		if (obj == this)
			return true;
		Point2D vec = (Point2D) obj;
		return x == vec.x && y == vec.y;
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
		return "Vector2D [x = " + x + ", y = " + y + "]";
	}
}