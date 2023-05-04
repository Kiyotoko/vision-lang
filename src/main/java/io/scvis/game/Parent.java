package io.scvis.game;

import java.util.List;

import javax.annotation.Nonnull;

public interface Parent extends Entity {

	@Override
	default void update(double deltaT) {
		for (int i = 0; i < getChildren().size(); i++) {
			getChildren().get(i).update(deltaT);
		}
	}

	@Nonnull
	List<? extends Children> getChildren();
}
