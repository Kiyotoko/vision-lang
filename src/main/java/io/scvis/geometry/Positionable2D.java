package io.scvis.geometry;

import javax.annotation.Nonnull;

public interface Positionable2D {

	@Nonnull
	Vector2D getPosition();

	void setPosition(@Nonnull Vector2D position);
}
