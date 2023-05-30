package io.scvis.proto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Table<R, C, V> {

	@Nullable
	V get(@Nonnull R row, @Nonnull C column);

	@Nullable
	V set(@Nonnull R row, @Nonnull C column, @Nullable V value);

	@Nonnull
	Iterable<R> getRows();

	@Nonnull
	Iterable<C> getColumns();

	@Nonnull
	Iterable<Cell<R, C, V>> getCells();

	@Nonnull
	Row<R, C, V> getRow(@Nonnull R row);

	@Nonnull
	Column<R, C, V> getColumn(@Nonnull C column);

	boolean containsRow(Object row);

	boolean containsColumn(Object column);

	void clear();

	interface Cell<R, C, V> {
		@Nonnull
		R getRow();

		@Nonnull
		C getColumn();

		@Nullable
		V getValue();

		@Nullable
		V setValue(V value);
	}

	interface Row<R, C, V> {
		@Nonnull
		R getRow();

		@Nonnull
		Iterable<C> getColumns();

		@Nonnull
		Iterable<V> getValues();

		@Nonnull
		Iterable<Cell<R, C, V>> getCells();
	}

	interface Column<R, C, V> {
		@Nonnull
		C getColumn();

		@Nonnull
		Iterable<R> getRows();

		@Nonnull
		Iterable<V> getValues();

		@Nonnull
		Iterable<Cell<R, C, V>> getCells();
	}
}
