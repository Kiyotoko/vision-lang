package org.scvis.math;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TestPoint2D {

	@ParameterizedTest
	@MethodSource("vectors")
	void subtract(@Nonnull Point2D exp, @Nonnull Point2D val) {
		assertEquals(exp, val.subtract(new Point2D(100, 100)));
	}

	@Test
	void distance() {
		assertEquals(Math.sqrt(2), new Point2D(1, 1).distance(Point2D.ZERO));
	}

	static Stream<Arguments> vectors() {
		return Stream.of(Arguments.of(new Point2D(100, 200), new Point2D(200, 300)),
				Arguments.of(new Point2D(400, 200), new Point2D(500, 300)),
				Arguments.of(new Point2D(0, 0), new Point2D(100, 100)));
	}
}
