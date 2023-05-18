package io.scvis.proto;

public interface Table<R, C, V> {

	V get(R row, C column);

	V set(R row, C column, V value);
}
