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
        Map<String, Map<String, ? extends E>> getContainer();

        Map<String, Supplier<? extends E>> getCreators();

        default E save(Map<String, E> map, String type, String id) {
        	E reference = map.get(id);
            if ((reference = map.get(id)) == null) {
                E newValue;
                if ((newValue = getCreators().get(type).get()) != null) {
                    map.put(id, newValue);
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
