package io.scvis.geometry;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.scvis.proto.Corresponding;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonDeserialize
public class Vector2D implements Corresponding<io.scvis.grpc.geometry.Vector2D> {
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

	@JsonCreator
	public Vector2D(@JsonProperty("x") double x, @JsonProperty("y") double y) {
		this.x = x;
		this.y = y;
	}

	@Nonnull
	public Vector2D add(@Nonnull Vector2D v) {
		return add(v.getX(), v.getY());
	}

	@Nonnull
	public Vector2D add(double x, double y) {
		return new Vector2D(this.x + x, this.y + y);
	}

	@Nonnull
	public Vector2D subtract(@Nonnull Vector2D v) {
		return subtract(v.getX(), v.getY());
	}

	@Nonnull
	public Vector2D subtract(double x, double y) {
		return new Vector2D(this.x - x, this.y - y);
	}

	@Nonnull
	public Vector2D multiply(double s) {
		return new Vector2D(x * s, y * s);
	}

	public double dotProduct(@Nonnull Vector2D v) {
		return dotProduct(v.x, v.y);
	}

	public double dotProduct(double x, double y) {
		return this.x * x + this.y * y;
	}

	public double distance(@Nonnull Vector2D v) {
		return distance(v.x, v.y);
	}

	public double distance(double x, double y) {
		double dx = this.x - x;
		double dy = this.x - y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	@Nonnull
	public Vector2D normalize() {
		double mag = magnitude();
		if (mag == 0.0) {
			return ZERO;
		}
		return new Vector2D(x / mag, y / mag);
	}

	@Nonnull
	public Vector2D midpoint(@Nonnull Vector2D v) {
		return midpoint(v.x, v.y);
	}

	@Nonnull
	public Vector2D midpoint(double x, double y) {
		return new Vector2D(x + (this.x - x) / 2.0, y + (this.y - y) / 2.0);
	}

	public double angle(@Nonnull Vector2D v) {
		return angle(v.getX(), v.getY());
	}

	public double angle(double x, double y) {
		return Math.atan2(this.y - y, this.x - x);
	}

	@Nonnull
	public Vector2D rotate(double a) {
		return new Vector2D(Math.cos(a) * x - Math.sin(a) * y, Math.sin(a) * x + Math.cos(a) * y);
	}

	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	public double getX() {
		return x;
	}

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

	@Override
	public String toString() {
		return "Vector [x = " + x + ", y = " + y + "]";
	}

	@Override
	public io.scvis.grpc.geometry.Vector2D associated() {
		return io.scvis.grpc.geometry.Vector2D.newBuilder().setX(x).setY(y).build();
	}
}