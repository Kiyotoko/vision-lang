package io.scvis.geometry;

import java.io.Serializable;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.scvis.proto.Corresponding;

/**
 * The Vector2D class represents a 2-dimensional vector in geometry.
 * 
 * @author karlz
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonDeserialize
public class Vector2D implements Corresponding<io.scvis.grpc.geometry.Vector2D>, Serializable {

	private static final long serialVersionUID = -9019588241960612260L;

	public static final @Nonnull Vector2D ZERO = new Vector2D(0.0, 0.0);

	/**
	 * The x coordinate of the vector.}
	 */
	@JsonProperty("x")
	private final double x;

	/**
	 * The y coordinate of the vector.}
	 */
	@JsonProperty("y")
	private final double y;

	/**
	 * Creates a Vector2D object with the given x and y coordinates.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	@JsonCreator
	public Vector2D(@JsonProperty("x") double x, @JsonProperty("y") double y) {
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
	@Nonnull
	public Vector2D add(@Nonnull Vector2D v) {
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
	@Nonnull
	public Vector2D add(double x, double y) {
		return new Vector2D(this.x + x, this.y + y);
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
	public Vector2D subtract(@Nonnull Vector2D v) {
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
	@Nonnull
	public Vector2D subtract(double x, double y) {
		return new Vector2D(this.x - x, this.y - y);
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
	public Vector2D multiply(double s) {
		return new Vector2D(x * s, y * s);
	}

	/**
	 * Calculates the dot product between this vector and another vector.
	 *
	 * @param v the vector to calculate the dot product with
	 * @return the dot product between this vector and the given vector
	 */
	public double dotProduct(@Nonnull Vector2D v) {
		return dotProduct(v.x, v.y);
	}

	/**
	 * Calculates the dot product between this vector and the specified coordinates.
	 *
	 * @param x the x-coordinate of the vector
	 * @param y the y-coordinate of the vector
	 * @return the dot product between this vector and the specified coordinates
	 */
	public double dotProduct(double x, double y) {
		return this.x * x + this.y * y;
	}

	/**
	 * Calculates the distance between this vector and the given vector.
	 *
	 * @param v the vector to calculate the distance to
	 * @return the distance between the two vectors
	 */
	public double distance(@Nonnull Vector2D v) {
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
	@Nonnull
	public Vector2D normalize() {
		double mag = magnitude();
		if (mag == 0.0) {
			return ZERO;
		}
		return new Vector2D(x / mag, y / mag);
	}

	/**
	 * Calculates the midpoint between this vector and the specified coordinates.
	 *
	 * @param x the x-coordinate of the vector
	 * @param y the y-coordinate of the vector
	 * @return a new vector representing the midpoint between this vector and the
	 *         specified coordinates
	 */
	@Nonnull
	public Vector2D midpoint(@Nonnull Vector2D v) {
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
	@Nonnull
	public Vector2D midpoint(double x, double y) {
		return new Vector2D(x + (this.x - x) / 2.0, y + (this.y - y) / 2.0);
	}

	/**
	 * Calculates the angle between this vector and another vector.
	 *
	 * @param v the vector to calculate the angle to
	 * @return the angle between this vector and the given vector
	 */
	public double angle(@Nonnull Vector2D v) {
		return angle(v.getX(), v.getY());
	}

	/**
	 * Calculates the angle between this vector and the specified coordinates.
	 *
	 * @param x the x-coordinate of the vector
	 * @param y the y-coordinate of the vector
	 * @return the angle between this vector and the specified coordinates
	 */
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
	@Nonnull
	public Vector2D rotate(double a) {
		return new Vector2D(Math.cos(a) * x - Math.sin(a) * y, Math.sin(a) * x + Math.cos(a) * y);
	}

	/**
	 * Calculates the magnitude (length) of this vector.
	 *
	 * @return the magnitude of the vector
	 */
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Returns the x coordinate of this vector.
	 *
	 * @return the x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the x coordinate of this vector.
	 *
	 * @return the x coordinate
	 */
	public double getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vector2D))
			return false;
		if (obj == this)
			return true;
		Vector2D vec = (Vector2D) obj;
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

	@Override
	public io.scvis.grpc.geometry.Vector2D associated() {
		return io.scvis.grpc.geometry.Vector2D.newBuilder().setX(x).setY(y).build();
	}
}