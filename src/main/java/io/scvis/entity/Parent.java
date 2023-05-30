package io.scvis.entity;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * The Parent interface represents an entity that updates its childrens.
 */
public interface Parent extends Entity {

	/**
	 * Updates the parent and its children based on the elapsed time since the last
	 * update.
	 *
	 * @param deltaT the elapsed time in seconds since the last update
	 */
	@Override
	default void update(double deltaT) {
		for (int i = 0; i < getChildren().size(); i++) {
			getChildren().get(i).update(deltaT);
		}
	}

	/**
	 * Retrieves the children entities associated with the parent.
	 *
	 * @return a list of children entities
	 */
	@Nonnull
	List<? extends Children> getChildren();
}
