package io.scvis.observable;

import javax.annotation.Nullable;

public interface WrappedValue<T> extends WrappedObject {

	void setValue(@Nullable T value);

	T getValue();
}
