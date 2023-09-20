package org.scvis.physic;

import javax.annotation.OverridingMethodsMustInvokeSuper;

/**
 * The Animation interface represents an entity that can be animated.
 * 
 * @author karlz
 */
public interface Animation extends Entity {

	@OverridingMethodsMustInvokeSuper
	@Override
	default void update(double deltaT) {
		animate(deltaT);
	}

	/**
	 * This method is called to perform the animation based on the elapsed time.
	 * 
	 * @param deltaT The elapsed time since the last update.
	 */
	void animate(double deltaT);
}
