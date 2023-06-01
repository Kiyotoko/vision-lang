package io.scvis.proto;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
	default String getType() {
		return getClass().getSimpleName();
	}

	/**
	 * Retrieves the ID of the object.
	 *
	 * @return the ID as a hexadecimal string
	 */
	@JsonIgnore
	default String getId() {
		return Integer.toHexString(hashCode());
	}
}
