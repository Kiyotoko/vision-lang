package io.scvis.observable;

public interface Observable<T> {

	void addInvalidationListener(InvalidationListener<T> listener);

	void removeInvalidationListener(InvalidationListener<T> listener);
}
