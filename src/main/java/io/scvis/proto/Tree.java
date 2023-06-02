package io.scvis.proto;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Tree<T> {

	@Nullable
	T get(Object... edges);

	@Nullable
	T set(@Nullable T value, Object... edges);

	@Nonnull
	Node<T> at(Object... edges);

	@Nonnull
	Node<T> root();

	void clear();

	interface Node<T> {
		@Nullable
		T getValue();

		@Nullable
		T setValue(@Nullable T value);

		@Nonnull
		Map<Object, Node<T>> getRelations();
	}
}
