package org.scvis.physic;

import javax.annotation.OverridingMethodsMustInvokeSuper;

/**
 * The Kinetic interface represents a kinetic object that can move, velocitate
 * and accelerate.
 * 
 * @author karlz
 */
public interface Kinetic extends Entity {

	/**
	 * Updates the kinetic object's state by applying acceleration, velocity and
	 * displacement for the specified time interval.
	 *
	 * @param deltaT the time interval for acceleration
	 */
	@OverridingMethodsMustInvokeSuper
	@Override
	default void update(double deltaT) {
		accelerate(deltaT);
		velocitate(deltaT);
		displacement(deltaT);
	}

	/**
	 * Calculates and applies the acceleration of the kinetic object for the
	 * specified time interval.
	 *
	 * @param deltaT the time interval for acceleration
	 */
	void accelerate(double deltaT);

	/**
	 * Calculates and applies the velocity of the kinetic object for the specified
	 * time interval.
	 *
	 * @param deltaT the time interval for velocitate
	 */
	void velocitate(double deltaT);

	/**
	 * Calculates and applies the displacement of the kinetic object for the
	 * specified time interval.
	 *
	 * @param deltaT the time interval for displacement
	 */
	void displacement(double deltaT);
}
