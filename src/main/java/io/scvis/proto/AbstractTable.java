package io.scvis.proto;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractTable<R, C, V> implements Table<R, C, V> {

	protected Set<R> rows;
	protected Set<C> columns;

	@Nonnull
	@Override
	public Iterable<C> getColumns() {
		return columns;
	}

	@Nonnull
	@Override
	public Iterable<R> getRows() {
		return rows;
	}

	@Nonnull
	@Override
	public Row<R, C, V> getRow(R row) {
		return new TableRow(row);
	}

	@Nonnull
	@Override
	public Column<R, C, V> getColumn(C column) {
		return new TableColumn(column);
	}

	@Override
	public Iterable<Cell<R, C, V>> getCells() {
		return () -> new CellIterator();
	}

	@Override
	public boolean containsRow(Object row) {
		return rows.contains(row);
	}

	@Override
	public boolean containsColumn(Object column) {
		return columns.contains(column);
	}

	public class TableCell implements Cell<R, C, V> {
		@Nonnull
		private final R row;
		@Nonnull
		private final C column;

		public TableCell(@Nonnull R row, @Nonnull C column) {
			this.row = row;
			this.column = column;
		}

		@Nonnull
		@Override
		public R getRow() {
			return row;
		}

		@Nonnull
		@Override
		public C getColumn() {
			return column;
		}

		@Nullable
		@Override
		public V getValue() {
			return AbstractTable.this.get(row, column);
		}

		@Override
		public V setValue(@Nullable V value) {
			return AbstractTable.this.set(row, column, value);
		}

		@Override
		public String toString() {
			return "TableCell[row=" + row + ", column=" + column + ", value=" + getValue() + "]";
		}
	}

	public class RowIterator implements Iterator<V> {
		@Nonnull
		private final R row;

		public RowIterator(@Nonnull R row) {
			this.row = row;
		}

		@Nonnull
		private Iterator<C> columns = getColumns().iterator();

		@Override
		public boolean hasNext() {
			return columns.hasNext();
		}

		@Nonnull
		@Override
		public V next() {
			return get(row, columns.next());
		}
	}

	public class RowCellIterator implements Iterator<Cell<R, C, V>> {
		@Nonnull
		private final R row;

		public RowCellIterator(@Nonnull R row) {
			this.row = row;
		}

		@Nonnull
		private Iterator<C> columns = getColumns().iterator();

		@Override
		public boolean hasNext() {
			return columns.hasNext();
		}

		@Nonnull
		@Override
		public Cell<R, C, V> next() {
			return new TableCell(row, columns.next());
		}
	}

	public class TableRow implements Row<R, C, V> {
		@Nonnull
		private final R row;

		public TableRow(@Nonnull R row) {
			this.row = row;
		}

		@Nonnull
		@Override
		public R getRow() {
			return row;
		}

		@Nonnull
		@Override
		public Iterable<C> getColumns() {
			return AbstractTable.this.getColumns();
		}

		@Nonnull
		@Override
		public Iterable<V> getValues() {
			return () -> new RowIterator(row);
		}

		@Nonnull
		@Override
		public Iterable<Cell<R, C, V>> getCells() {
			return () -> new RowCellIterator(row);
		}
	}

	public class ColumnIterator implements Iterator<V> {
		@Nonnull
		private final C column;

		public ColumnIterator(@Nonnull C column) {
			this.column = column;
		}

		@Nonnull
		private Iterator<R> rows = getRows().iterator();

		@Override
		public boolean hasNext() {
			return rows.hasNext();
		}

		@Nonnull
		@Override
		public V next() {
			return get(rows.next(), column);
		}
	}

	public class ColumnCellIterator implements Iterator<Cell<R, C, V>> {
		@Nonnull
		private final C column;

		public ColumnCellIterator(@Nonnull C column) {
			this.column = column;
		}

		@Nonnull
		private Iterator<R> rows = getRows().iterator();

		@Override
		public boolean hasNext() {
			return rows.hasNext();
		}

		@Nonnull
		@Override
		public Cell<R, C, V> next() {
			return new TableCell(rows.next(), column);
		}
	}

	public class TableColumn implements Column<R, C, V> {
		@Nonnull
		private final C column;

		public TableColumn(@Nonnull C column) {
			this.column = column;
		}

		@Nonnull
		@Override
		public C getColumn() {
			return column;
		}

		@Nonnull
		@Override
		public Iterable<R> getRows() {
			return AbstractTable.this.getRows();
		}

		@Nonnull
		@Override
		public Iterable<V> getValues() {
			return () -> new ColumnIterator(column);
		}

		@Nonnull
		@Override
		public Iterable<Cell<R, C, V>> getCells() {
			return () -> new ColumnCellIterator(column);
		}
	}

	public class CellIterator implements Iterator<Cell<R, C, V>> {
		@Nonnull
		private Iterator<R> rows = getRows().iterator();
		@Nonnull
		private Iterator<C> columns = getColumns().iterator();

		@Nonnull
		private R current = rows.next();

		@Override
		public boolean hasNext() {
			return rows.hasNext() || columns.hasNext();
		}

		@Nonnull
		@Override
		public Cell<R, C, V> next() {
			if (columns.hasNext())
				return new TableCell(current, columns.next());
			else {
				current = rows.next();
				columns = getColumns().iterator();

				return new TableCell(current, columns.next());
			}
		}
	}
}
