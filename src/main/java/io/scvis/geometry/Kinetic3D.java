package io.scvis.geometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.scvis.entity.Kinetic;
import io.scvis.proto.Identifiable;

/**
 * The Kinetic3D abstract class represents a 3D kinetic entity with motion and
 * velocity.
 * 
 * @author karlz
 */
public abstract class Kinetic3D extends Layout3D implements Kinetic, Identifiable {

	private static final long serialVersionUID = -6421069856222021884L;

	private @Nonnull Vector3D acceleration = Vector3D.ZERO;
	private @Nonnull Vector3D velocity = Vector3D.ZERO;

	/**
	 * Constructs a Kinetic3D object with the specified local border, position,
	 * rotation angles, acceleration, and velocity.
	 * 
	 * @param local        The local border of the kinetic entity.
	 * @param position     The position of the kinetic entity.
	 * @param rotationA    The rotation angle A of the kinetic entity.
	 * @param rotationB    The rotation angle B of the kinetic entity.
	 * @param acceleration The acceleration vector of the kinetic entity.
	 * @param velocity     The velocity vector of the kinetic entity.
	 */
	protected Kinetic3D(@Nonnull Border3D local, @Nonnull Vector3D position, double rotationA, double rotationB,
			@Nonnull Vector3D acceleration, @Nonnull Vector3D velocity) {
		super(local, position, rotationA, rotationB);
		this.acceleration = acceleration;
		this.velocity = velocity;
	}

	@Nullable
	private Vector3D destination = null;

	/**
	 * Checks if the kinetic entity has a destination.
	 * 
	 * @return True if the kinetic entity has a destination, false otherwise.
	 */
	public boolean hasDestination() {
		return destination != null;
	}

	/**
	 * Returns the destination vector of the kinetic entity.
	 * 
	 * @return The destination vector of the kinetic entity.
	 */
	@Nonnull
	public Vector3D getDestination() {
		// Check for nonnull
		final Vector3D checked = this.destination;
		if (checked != null)
			return checked;
		return getPosition();
	}

	/**
	 * Sets the destination vector of the kinetic entity.
	 * 
	 * @param destination The destination vector to set.
	 */
	public void setDestination(@Nullable Vector3D destination) {
		this.destination = destination;
	}

	@Nullable
	private Border3D target = null;

	/**
	 * Checks if the kinetic entity has a target border.
	 * 
	 * @return True if the kinetic entity has a target border, false otherwise.
	 */
	public boolean hasTarget() {
		return target != null;
	}

	/**
	 * Returns the target border of the kinetic entity.
	 * 
	 * @return The target border of the kinetic entity.
	 */
	@Nonnull
	public Border3D getTarget() {
		// Check for nonnull
		final Border3D checked = this.target;
		if (checked != null)
			return checked;
		return this;
	}

	/**
	 * Sets the target border of the kinetic entity.
	 * 
	 * @param target The target border to set.
	 */
	public void setTarget(@Nullable Border3D target) {
		this.target = target;
	}

	/**
	 * Returns the acceleration vector of the kinetic entity.
	 * 
	 * @return The acceleration vector of the kinetic entity.
	 */
	@Nonnull
	public Vector3D getAcceleration() {
		return acceleration;
	}

	/**
	 * Sets the acceleration vector of the kinetic entity.
	 * 
	 * @param acceleration The acceleration vector to set.
	 */
	@Nonnull
	public void setAcceleration(Vector3D acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * Returns the velocity vector of the kinetic entity.
	 * 
	 * @return The velocity vector of the kinetic entity.
	 */
	@Nonnull
	public Vector3D getVelocity() {
		return velocity;
	}

	/**
	 * Sets the velocity vector of the kinetic entity.
	 * 
	 * @param velocity The velocity vector to set.
	 */
	public void setVelocity(@Nonnull Vector3D velocity) {
		this.velocity = velocity;
	}
}
