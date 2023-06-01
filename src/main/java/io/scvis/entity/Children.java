package io.scvis.entity;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

/**
 * The Children interface represents an entity that has parent-child
 * relationships within a game.
 * 
 * @author karlz
 * 
 * @see Entity
 * @see Destroyable
 */
public interface Children extends Entity, Destroyable {
	/**
	 * Destroys the entity. It removes the entity from its parent's children.
	 */
	@OverridingMethodsMustInvokeSuper
	@Override
	default void destroy() {
		getParent().getChildren().remove(this);
	}

	/**
	 * Retrieves the parent entity.
	 *
	 * @return the parent entity
	 */
	@Nonnull
	Parent getParent();
}
