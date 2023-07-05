package io.scvis.proto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A mapper is a function that maps every element of a collection to their
 * associated value.
 *
 * @param <K> the input key type
 * @param <V> the generated value type
 * @author karlz
 * @see Function
 * @see Collection
 * @see Stream
 */
@FunctionalInterface
public interface Mapper<K, V> extends Function<K, V> {
	/**
	 * Creates a Mapper instance from the provided mapper.
	 * 
	 * @param mapper the mapper function
	 * @param <K>    the input key type
	 * @param <V>    the generated value type
	 * @return a new Mapper instance
	 */
	public static <K, V> Mapper<K, V> create(Mapper<K, V> mapper) {
		return mapper;
	}

	/**
	 * Maps every element of the list to a new value and returns a new list.
	 * 
	 * @param list the list with the keys
	 * @return a new ArrayList containing the mapped values
	 */
	default List<? extends V> map(List<? extends K> list) {
		return list.stream().map(this).collect(Collectors.toList());
	}

	/**
	 * Maps every element of the set to a new value and returns a new set.
	 * 
	 * @param set the set with the keys
	 * @return a new HashSet containing the mapped values
	 */
	default Set<? extends V> map(Set<? extends K> set) {
		return set.stream().map(this).collect(Collectors.toSet());
	}

	/**
	 * Maps every element of the list to a new value and returns a new list.
	 * 
	 * @param set the set with the keys
	 * @return a new ArrayList containing the mapped values
	 */
	default Collection<? extends V> map(Collection<? extends K> collection) {
		return collection.stream().map(this).collect(Collectors.toCollection(ArrayList::new));
	}
}
