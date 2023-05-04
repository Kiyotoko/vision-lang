package io.scvis.game;

/**
 * Entities are objects in a discrete simulation that can be updated over time.
 * 
 * @author karlz
 * @see Parent
 * @see Children
 * @see #update(double)
 */
public interface Entity {

	/**
	 * updates the object with a time difference of deltaT time units.
	 * 
	 * @param deltaT The time difference.
	 */
	void update(double deltaT);
}
