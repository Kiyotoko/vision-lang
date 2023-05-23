package io.scvis.game;

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
	@Override
	default void destroy() {
		getParent().getChildren().remove(this);
	}

	/**
	 * Retrieves the parent entity.
	 *
	 * @return the parent entity
	 */
	Parent getParent();
}
