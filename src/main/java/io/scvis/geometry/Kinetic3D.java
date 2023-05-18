package io.scvis.geometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.scvis.proto.Identifiable;

public abstract class Kinetic3D extends Layout3D implements Kinetic, Identifiable {

	@JsonProperty(value = "acceleration", index = 17)
	private Vector3D acceleration = Vector3D.ZERO;
	@JsonProperty(value = "velocity", index = 18)
	private Vector3D velocity = Vector3D.ZERO;

	@JsonCreator
	protected Kinetic3D(@JsonProperty("local") @Nonnull Border3D local,
			@JsonProperty("position") @Nonnull Vector3D position, @JsonProperty("rotationA") double rotationA,
			@JsonProperty("rotationB") double rotationB, @JsonProperty("acceleration") Vector3D acceleration,
			@JsonProperty("velocity") Vector3D velocity) {
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
		// Check for nonnull
		final Vector3D checked = this.destination;
		if (checked != null)
			return checked;
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
		// Check for nonnull
		final Border3D checked = this.target;
		if (checked != null)
			return checked;
		return this;
	}

	public void setTarget(@Nullable Border3D target) {
		this.target = target;
	}

	public Vector3D getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector3D acceleration) {
		this.acceleration = acceleration;
	}

	public Vector3D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3D velocity) {
		this.velocity = velocity;
	}
}
