package io.scvis.proto;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public interface ExchangeHelper<E> {

	public static interface DispatchHelper<E, C> extends ExchangeHelper<E>, Corresponding<C> {
		void clean();

		default void push(Collection<E> collection, E element) {
			collection.add(element);
		}

		default C dispatch() {
			try {
				return associated();
			} finally {
				clean();
			}
		}
	}

	public static interface StoreHelper<E, R> extends ExchangeHelper<E>, Reference<R> {
		Table<Object, Object, ? extends E> getContainer();

		Map<Object, Supplier<? extends E>> getCreators();

		default E save(Table<Object, Object, E> table, Object type, Object id) {
			E reference = table.get(type, id);
			if (reference == null) {
				E newValue;
				if ((newValue = getCreators().get(type).get()) != null) {
					table.set(type, id, newValue);
					return newValue;
				}
			}
			return reference;
		}

		default void store(R reference) {
			update(reference);
		}
	}

}
