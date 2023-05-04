package io.scvis.geometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.scvis.proto.Identifiable;

public abstract class Kinetic3D extends Layout3D implements Kinetic, Identifiable {

	@JsonProperty(value = "acceleration", index = 17)
	private double acceleration = 0.0;
	@JsonProperty(value = "velocity", index = 18)
	private double velocity = 0.0;
	@JsonProperty(value = "circularSpeed", index = 19)
	private double circularSpeed = 5;

	@JsonCreator
	protected Kinetic3D(@JsonProperty("local") @Nonnull Border3D local,
			@JsonProperty("position") @Nonnull Vector3D position, @JsonProperty("rotationA") double rotationA,
			@JsonProperty("rotationB") double rotationB, @JsonProperty("acceleration") double acceleration,
			@JsonProperty("velocity") double velocity) {
		super(local, position, rotationA, rotationB);
		this.acceleration = acceleration;
		this.velocity = velocity;
	}

	@JsonProperty(value = "destination", index = 16)
	@Nullable
	private Vector3D destination = null;

	public boolean hasDestination() {
		return destination != null;
	}

	@Nonnull
	public Vector3D getDestination() {
		final Vector3D destination = this.destination;
		if (destination != null)
			return destination;
		return getPosition();
	}

	public void setDestination(@Nullable Vector3D destination) {
		this.destination = destination;
	}

	@JsonIgnore
	@Nullable
	private Border3D target = null;

	public boolean hasTarget() {
		return target != null;
	}

	@Nonnull
	public Border3D getTarget() {
		final Border3D target = this.target;
		if (target != null)
			return target;
		return this;
	}

	public void setTarget(@Nullable Border3D target) {
		this.target = target;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getCircularSpeed() {
		return circularSpeed;
	}

	public void setCircularSpeed(double circularSpeed) {
		this.circularSpeed = circularSpeed;
	}
}
