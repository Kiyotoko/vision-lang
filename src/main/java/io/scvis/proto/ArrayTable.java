package io.scvis.proto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ArrayTable<R, C, V> extends AbstractTable<R, C, V> {

	private final Map<R, Integer> rowsToIndex;
	private final Map<C, Integer> columnsToIndex;

	private final V[][] fields;

	public ArrayTable(Set<R> rows, Set<C> columns) {
		this.rowsToIndex = mapToIndex(rows);
		this.columnsToIndex = mapToIndex(columns);

		super.rows = rows;
		super.columns = columns;
		@SuppressWarnings("unchecked")
		V[][] array = (V[][]) new Object[rows.size()][columns.size()];
		this.fields = array;
	}

	private <T> Map<T, Integer> mapToIndex(Collection<T> iter) {
		Map<T, Integer> generated = new HashMap<>(iter.size());
		Iterator<T> iterator = iter.iterator();
		for (int index = 0; iterator.hasNext(); index++)
			generated.put(iterator.next(), index);
		return generated;
	}

	public V get(int row, int column) {
		return fields[row][column];
	}

	public V set(int row, int column, V value) {
		V previous = get(row, column);
		fields[row][column] = value;
		return previous;
	}

	@Override
	public V get(R row, C column) {
		return get(rowsToIndex.get(row), columnsToIndex.get(column));
	}

	@Override
	public V set(R row, C column, V value) {
		return set(rowsToIndex.get(row), columnsToIndex.get(column), value);
	}

	@Override
	public void clear() {
		for (int r = 0, lenR = fields.length; r < lenR; r++)
			for (int c = 0, lenC = fields[r].length; c < lenC; c++)
				fields[r][c] = null;
	}
}
