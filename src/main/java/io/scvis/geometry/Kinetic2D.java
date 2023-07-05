package io.scvis.geometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.scvis.entity.Kinetic;
import io.scvis.proto.Identifiable;

/**
 * The Kinetic2D abstract class represents a 2D kinetic entity with motion and
 * velocity.
 * 
 * @author karlz
 */
public abstract class Kinetic2D extends Layout2D implements Kinetic, Identifiable {

	private static final long serialVersionUID = -426988271831404911L;

	private @Nonnull Vector2D acceleration = Vector2D.ZERO;
	private @Nonnull Vector2D velocity = Vector2D.ZERO;

	/**
	 * Constructs a Kinetic2D object with the specified local border, position,
	 * rotation, acceleration, and velocity.
	 * 
	 * @param local        The local border of the kinetic entity.
	 * @param position     The position of the kinetic entity.
	 * @param rotation     The rotation angle of the kinetic entity.
	 * @param acceleration The acceleration vector of the kinetic entity.
	 * @param velocity     The velocity vector of the kinetic entity.
	 */
	protected Kinetic2D(@Nonnull Border2D local, @Nonnull Vector2D position, double rotation, Vector2D acceleration,
			Vector2D velocity) {
		super(local, position, rotation);
		this.acceleration = acceleration;
		this.velocity = velocity;
	}

	@Nullable
	private Vector2D destination = null;

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
	public Vector2D getDestination() {
		final Vector2D checked = this.destination;
		if (checked != null)
			return checked;
		return getPosition();
	}

	/**
	 * Sets the destination vector of the kinetic entity.
	 * 
	 * @param destination The destination vector to set.
	 */
	public void setDestination(@Nullable Vector2D destination) {
		this.destination = destination;
	}

	@Nullable
	private Border2D target = null;

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
	public Border2D getTarget() {
		final Border2D checked = this.target;
		if (checked != null)
			return checked;
		return this;
	}

	/**
	 * Sets the target border of the kinetic entity.
	 * 
	 * @param target The target border to set.
	 */
	public void setTarget(@Nullable Border2D target) {
		this.target = target;
	}

	/**
	 * 
	 * Returns the acceleration vector of the kinetic entity.
	 * 
	 * @return The acceleration vector of the kinetic entity.
	 */
	@Nonnull
	public Vector2D getAcceleration() {
		return acceleration;
	}

	/**
	 * Sets the acceleration vector of the kinetic entity.
	 * 
	 * @param acceleration The acceleration vector to set.
	 */
	public void setAcceleration(@Nonnull Vector2D acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * Returns the velocity vector of the kinetic entity.
	 * 
	 * @return The velocity vector of the kinetic entity.
	 */
	@Nonnull
	public Vector2D getVelocity() {
		return velocity;
	}

	/**
	 * Sets the velocity vector of the kinetic entity.
	 * 
	 * @param velocity The velocity vector to set.
	 */
	public void setVelocity(@Nonnull Vector2D velocity) {
		this.velocity = velocity;
	}
}
