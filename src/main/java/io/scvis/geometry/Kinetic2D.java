package io.scvis.geometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.scvis.proto.Identifiable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonDeserialize
public abstract class Kinetic2D extends Layout2D implements Kinetic, Identifiable {

	@JsonProperty(value = "acceleration", index = 17)
	private Vector2D acceleration = Vector2D.ZERO;
	@JsonProperty(value = "velocity", index = 18)
	private Vector2D velocity = Vector2D.ZERO;

	@JsonCreator
	public Kinetic2D(@JsonProperty("local") @Nonnull Border2D local,
			@JsonProperty("position") @Nonnull Vector2D position, @JsonProperty("rotation") double rotation,
			@JsonProperty("acceleration") Vector2D acceleration, @JsonProperty("velocity") Vector2D velocity) {
		super(local, position, rotation);
		this.acceleration = acceleration;
		this.velocity = velocity;
	}

	@JsonProperty(value = "destination", index = 16)
	@Nullable
	private Vector2D destination = null;

	public boolean hasDestination() {
		return destination != null;
	}

	@Nonnull
	public Vector2D getDestination() {
		final Vector2D destination = this.destination;
		if (destination != null)
			return destination;
		return getPosition();
	}

	public void setDestination(@Nullable Vector2D destination) {
		this.destination = destination;
	}

	@JsonIgnore
	@Nullable
	private Border2D target = null;

	public boolean hasTarget() {
		return target != null;
	}

	@Nonnull
	public Border2D getTarget() {
		final Border2D target = this.target;
		if (target != null)
			return target;
		return this;
	}

	public void setTarget(@Nullable Border2D target) {
		this.target = target;
	}

	public Vector2D getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}
}
