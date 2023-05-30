package io.scvis.observable;

import javax.annotation.Nonnull;

public interface Observable<T> {

	void addInvalidationListener(@Nonnull InvalidationListener<T> listener);

	void removeInvalidationListener(@Nonnull InvalidationListener<T> listener);
}
