package io.scvis.proto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.protobuf.Message;

import io.scvis.proto.Corresponding.ExtendableCorresponding;

public interface Identifiable extends ExtendableCorresponding {
	@JsonIgnore
	default String getType() {
		return getClass().getSimpleName();
	}

	@JsonIgnore
	default String getId() {
		return Integer.toHexString(hashCode());
	}

	@Override
	default Message associated() {
		return io.scvis.grpc.proto.Identifiable.newBuilder().setId(getId()).setType(getType()).build();
	}
}
