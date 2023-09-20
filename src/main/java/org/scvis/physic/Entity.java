package org.scvis.physic;

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
	 * Updates the object with a time difference of deltaT time units.
	 * 
	 * @param deltaT the elapsed time since the last update
	 */
	void update(double deltaT);
}
