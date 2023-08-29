package io.scvis.math;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TestVector2D {

	@ParameterizedTest
	@MethodSource("vectors")
	void subtract(@Nonnull Vector2D exp, @Nonnull Vector2D val) {
		assertEquals(exp, val.subtract(new Vector2D(100, 100)));
	}

	@Test
	void distance() {
		assertEquals(Math.sqrt(2), new Vector2D(1, 1).distance(Vector2D.ZERO));
	}

	static Stream<Arguments> vectors() {
		return Stream.of(Arguments.of(new Vector2D(100, 200), new Vector2D(200, 300)),
				Arguments.of(new Vector2D(400, 200), new Vector2D(500, 300)),
				Arguments.of(new Vector2D(0, 0), new Vector2D(100, 100)));
	}
}
