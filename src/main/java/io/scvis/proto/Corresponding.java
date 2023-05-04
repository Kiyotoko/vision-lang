package io.scvis.proto;

import java.util.Collection;

import com.google.protobuf.Message;

public interface Corresponding<T> {
	T associated();

	public static interface ExtendableCorresponding extends Corresponding<Message> {

	}

	public static <T> T transform(Corresponding<T> corresponding) {
		return corresponding.associated();
	}

	public static <T> Collection<? extends T> transform(Collection<? extends Corresponding<T>> corresponding) {
		return Mapper.create((Corresponding<? extends T> c) -> c.associated()).map(corresponding);
	}
}
