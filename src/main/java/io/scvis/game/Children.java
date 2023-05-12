package io.scvis.game;

public interface Children extends Entity, Destroyable {

	@Override
	default void destroy() {
		getParent().getChildren().remove(this);
	}

	Parent getParent();
}
