package io.scvis.observable;

import java.util.EventListener;
import java.util.EventObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@FunctionalInterface
public interface ChangeListener<T> extends EventListener {

	void changed(@Nonnull ChangeEvent<T> event);

	class ChangeEvent<T> extends EventObject {

		private static final long serialVersionUID = -3032299925044664903L;

		@Nonnull
		private final Property<T> property;
		@Nullable
		private final T o;
		@Nullable
		private final T n;

		public ChangeEvent(@Nonnull Property<T> property, @Nullable T o, @Nullable T n) {
			super(property);
			this.property = property;
			this.o = o;
			this.n = n;
		}

		@Nonnull
		public Property<T> getProperty() {
			return property;
		}

		@Nullable
		public T getOld() {
			return o;
		}

		@Nullable
		public T getNew() {
			return n;
		}

		@Override
		public String toString() {
			return property + " changed from " + o + " to " + n;
		}
	}
}
