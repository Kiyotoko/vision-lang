package io.scvis.entity;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public interface Animation extends Entity {

	@OverridingMethodsMustInvokeSuper
	@Override
	default void update(double deltaT) {
		animate(deltaT);
	}

	void animate(double deltaT);
}
