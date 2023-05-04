package io.scvis.game;

public interface Board<R, C, V> {

	V get(R row, C column);

	V set(R row, C column, V value);
}
