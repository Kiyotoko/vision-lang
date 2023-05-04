package io.scvis.game;

public interface Children extends Entity {

	default void destroy() {
		getParent().getChildren().remove(this);
	}

	Parent getParent();
}
