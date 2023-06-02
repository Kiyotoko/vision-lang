package io.scvis.proto;

import java.util.HashMap;
import java.util.Map;

public class HashTable<R, C, V> extends AbstractTable<R, C, V> {

	private final Map<R, Map<C, V>> fields = new HashMap<>();

	public HashTable() {

	}

	private Map<C, V> get(R row) {
		if (!containsRow(row)) {
			getRows().add(row);
			fields.put(row, new HashMap<>());
		}
		return fields.get(row);
	}

	@Override
	public V get(R row, C column) {
		return get(row).get(column);
	}

	@Override
	public V set(R row, C column, V value) {
		if (!containsColumn(column)) {
			getColumns().add(column);
		}
		return get(row).put(column, value);
	}

	@Override
	public void clear() {
		fields.clear();
		getRows().clear();
		getColumns().clear();
	}
}
