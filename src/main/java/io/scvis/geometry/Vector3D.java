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
 * The Vector3D class represents a 3-dimensional vector in geometry.
 * 
 * @author karlz
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonDeserialize
public class Vector3D implements Corresponding<io.scvis.grpc.geometry.Vector3D>, Serializable {

	private static final long serialVersionUID = -4200231978633862588L;

	public static final @Nonnull Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);

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
	 * The z coordinate of the vector.}
	 */
	@JsonProperty("z")
	private final double z;

	/**
	 * Creates a Vector3D object with the given x, y and z coordinates.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	@JsonCreator
	public Vector3D(@JsonProperty("x") double x, @JsonProperty("y") double y, @JsonProperty("z") double z) {
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
	public Vector3D add(@Nonnull Vector3D v) {
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
	public Vector3D add(double x, double y, double z) {
		return new Vector3D(this.x + x, this.y + y, this.z + z);
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
	public Vector3D subtract(@Nonnull Vector3D v) {
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
	public Vector3D subtract(double x, double y, double z) {
		return new Vector3D(this.x - x, this.y - y, this.z - z);
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
	public Vector3D multiply(double s) {
		return new Vector3D(x * s, y * s, z * s);
	}

	/**
	 * Calculates the distance between this vector and another vector.
	 *
	 * @param v the vector to calculate the distance to
	 * @return the distance between this vector and the given vector
	 */
	public double distance(@Nonnull Vector3D v) {
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
	public Vector3D normalize() {
		double mag = magnitude();
		if (mag == 0.0) {
			return ZERO;
		}
		return new Vector3D(x / mag, y / mag, z / mag);
	}

	/**
	 * Calculates the midpoint between this vector and another vector.
	 *
	 * @param v the vector to calculate the midpoint with
	 * @return a new vector representing the midpoint between this vector and the
	 *         given vector
	 */
	@Nonnull
	public Vector3D midpoint(@Nonnull Vector3D v) {
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
	public Vector3D midpoint(double x, double y, double z) {
		return new Vector3D(x + (this.x - x) / 2.0, y + (this.y - y) / 2.0, z + (this.z - z) / 2);
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
	public boolean equals(Object obj) {
		if (!(obj instanceof Vector3D))
			return false;
		if (obj == this)
			return true;
		Vector3D vec = (Vector3D) obj;
		return x == vec.x && y == vec.y && z == vec.z;
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
		return "Vector3D [x = " + x + ", y = " + y + ", z = " + z + "]";
	}

	@Override
	public io.scvis.grpc.geometry.Vector3D associated() {
		return io.scvis.grpc.geometry.Vector3D.newBuilder().setX(x).setY(y).setZ(z).build();
	}
}
