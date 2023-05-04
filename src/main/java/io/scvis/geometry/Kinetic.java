package io.scvis.geometry;

import io.scvis.game.Entity;

public interface Kinetic extends Entity {

	@Override
	default void update(double deltaT) {
		accelerate(deltaT);
		velocitate(deltaT);
		displacement(deltaT);
	}

	void accelerate(double deltaT);

	void velocitate(double deltaT);

	void displacement(double deltaT);
}
