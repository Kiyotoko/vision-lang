package io.scvis.observable;

import java.util.EventListener;
import java.util.EventObject;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface InvalidationListener<T> extends EventListener {

	void invalidated(@Nonnull InvalidationEvent<T> event);

	static class InvalidationEvent<T> extends EventObject {

		private static final long serialVersionUID = -2374322950935754101L;

		@Nonnull
		private final Observable<T> observable;

		public InvalidationEvent(@Nonnull Observable<T> observable) {
			super(observable);
			this.observable = observable;
		}

		@Nonnull
		public Observable<T> getObservable() {
			return observable;
		}

		@Override
		public String toString() {
			return "invalidated " + observable;
		}
	}
}
