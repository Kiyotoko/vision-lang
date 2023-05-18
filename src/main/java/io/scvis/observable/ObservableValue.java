package io.scvis.observable;

public interface ObservableValue<T> extends Observable<T> {

	T get();

	void set(T value);
}
