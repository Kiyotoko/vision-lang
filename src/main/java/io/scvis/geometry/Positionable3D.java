package io.scvis.geometry;

import javax.annotation.Nonnull;

public interface Positionable3D {

	@Nonnull
	Vector3D getPosition();

	void setPosition(@Nonnull Vector3D position);
}
