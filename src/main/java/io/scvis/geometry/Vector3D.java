package io.scvis.geometry;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonDeserialize
public class Vector3D {
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

	@JsonCreator
	public Vector3D(@JsonProperty("x") double x, @JsonProperty("y") double y, @JsonProperty("z") double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Nonnull
	public Vector3D add(@Nonnull Vector3D v) {
		return add(v.getX(), v.getY(), v.getZ());
	}

	@Nonnull
	public Vector3D add(double x, double y, double z) {
		return new Vector3D(this.x + x, this.y + y, this.z + z);
	}

	@Nonnull
	public Vector3D subtract(@Nonnull Vector3D v) {
		return subtract(v.getX(), v.getY(), v.getZ());
	}

	@Nonnull
	public Vector3D subtract(double x, double y, double z) {
		return new Vector3D(this.x - x, this.y - y, this.z - z);
	}

	@Nonnull
	public Vector3D multiply(double s) {
		return new Vector3D(x * s, y * s, z * s);
	}

	public double distance(@Nonnull Vector3D v) {
		return distance(v.x, v.y, v.z);
	}

	public double distance(double x, double y, double z) {
		double dx = this.x - x;
		double dy = this.x - y;
		double dz = this.z - z;
		return Math.sqrt(Math.sqrt(dx * dx + dy * dy) + dz * dz);
	}

	@Nonnull
	public Vector3D normalize() {
		double mag = magnitude();
		if (mag == 0.0) {
			return ZERO;
		}
		return new Vector3D(x / mag, y / mag, z / mag);
	}

	@Nonnull
	public Vector3D midpoint(@Nonnull Vector3D v) {
		return midpoint(v.x, v.y, v.z);
	}

	@Nonnull
	public Vector3D midpoint(double x, double y, double z) {
		return new Vector3D(x + (this.x - x) / 2.0, y + (this.y - y) / 2.0, z + (this.z - z) / 2);
	}

	public double magnitude() {
		return Math.sqrt(Math.sqrt(x * x + y * y) + z * z);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vector2D))
			return false;
		if (obj == this)
			return true;
		Vector3D vec = (Vector3D) obj;
		return x == vec.x && y == vec.y && z == vec.z;
	}

	@Override
	public String toString() {
		return "Vector [x = " + x + ", y = " + y + ", z = " + z + "]";
	}
}
