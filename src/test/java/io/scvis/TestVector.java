package io.scvis;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.scvis.geometry.Vector2D;

public class TestVector {

	@ParameterizedTest
	@MethodSource("vectors")
	void add(@Nonnull Vector2D vector2D) {
		System.out.println(new Vector2D(100, 100).add(vector2D));
	}

	@ParameterizedTest
	@MethodSource("vectors")
	void distance(@Nonnull Vector2D vector2D) {
		System.out.println(new Vector2D(100, 100).distance(vector2D));
	}

	static Stream<Arguments> vectors() {
		return Stream.of(Arguments.of(new Vector2D(100, 200)), Arguments.of(new Vector2D(400, 200)),
				Arguments.of(new Vector2D(0, 0)));
	}
}
