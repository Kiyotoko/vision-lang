package io.scvis.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.scvis.geometry.Shape.Circle;

class TestShape {

	@Test
	void translation() {
		assertEquals(new Circle(new Vector2D(40, 40), 10).translate(50, 20).getCenter(), new Vector2D(90, 60));
	}
}
