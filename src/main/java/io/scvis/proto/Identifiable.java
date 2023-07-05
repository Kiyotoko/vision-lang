package io.scvis.proto;

/**
 * The Identifiable interface represents an object that can be identified with a
 * id and type.
 * 
 * @author karlz
 */
public interface Identifiable {
	/**
	 * Retrieves the type of the object.
	 *
	 * @return the type as a string
	 */
	default String getType() {
		return getClass().getSimpleName();
	}

	/**
	 * Retrieves the ID of the object.
	 *
	 * @return the ID as a hexadecimal string
	 */
	default String getId() {
		return Integer.toHexString(hashCode());
	}
}
