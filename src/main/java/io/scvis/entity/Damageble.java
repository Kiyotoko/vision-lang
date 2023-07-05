package io.scvis.entity;

/**
 * The Damageble interface represents an entity that can be damaged.
 * 
 * @author karlz
 */
public interface Damageble extends Destroyable {

	/**
	 * This method is called to apply damage to the entity.
	 * 
	 * @param damage The amount of damage to be applied.
	 */
	void damage(double damage);
}
