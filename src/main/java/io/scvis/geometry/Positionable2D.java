package io.scvis.geometry;

import javax.annotation.Nonnull;

/**
 * A positionable is an object or entity that has the capability of being placed
 * or located in various positions within a given 2d space or coordinate system.
 * 
 * @author karlz
 */
public interface Positionable2D {
	/**
	 * Returns the position of the object in 2D space.
	 *
	 * @return The position of the object.
	 */
	@Nonnull
	Vector2D getPosition();

	/**
	 * Sets the position of the object in 2D space.
	 *
	 * @param position The position to set.
	 */
	void setPosition(@Nonnull Vector2D position);
}
