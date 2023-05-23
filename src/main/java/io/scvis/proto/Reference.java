package io.scvis.proto;

/**
 * The Reference interface represents an object that can be updated with a new
 * reference.
 *
 * @author karlz
 *
 * @param <T> the type of the reference
 */
public interface Reference<T> {
	/**
	 * Updates the reference with a new value.
	 *
	 * @param reference the new reference value
	 */
	void update(T reference);
}
