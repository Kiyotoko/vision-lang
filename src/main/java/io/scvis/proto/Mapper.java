package io.scvis.proto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Mapper<K, V> extends Function<K, V> {
	public static <K, V> Mapper<K, V> create(Mapper<K, V> mapper) {
		return mapper;
	}

	default List<? extends V> map(List<? extends K> list) {
		return list.stream().map(this).collect(Collectors.toList());
	}

	default Set<? extends V> map(Set<? extends K> set) {
		return set.stream().map(this).collect(Collectors.toSet());
	}

	default Collection<? extends V> map(Collection<? extends K> collection) {
		return collection.stream().map(this).collect(Collectors.toCollection(() -> new ArrayList<>()));
	}
}
