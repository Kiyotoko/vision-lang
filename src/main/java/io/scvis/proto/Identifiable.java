package io.scvis.proto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.protobuf.Message;

import io.scvis.proto.Corresponding.ExtendableCorresponding;

/**
 * The Identifiable interface represents an object that can be identified with a
 * id and type.
 * 
 * @author karlz
 */
public interface Identifiable extends ExtendableCorresponding {
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

	/**
	 * Returns the associated Message object.
	 *
	 * @return the associated Message object
	 */
	@Override
	default Message associated() {
		return io.scvis.grpc.proto.Identifiable.newBuilder().setId(getId()).setType(getType()).build();
	}
}
