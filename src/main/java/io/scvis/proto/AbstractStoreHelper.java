package io.scvis.proto;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.scvis.proto.ExchangeHelper.StoreHelper;

/**
 * @author karlz
 */
public abstract class AbstractStoreHelper<R> implements StoreHelper<Reference<?>, R> {
	private final Map<Object, Supplier<? extends Reference<?>>> creators = new HashMap<>();
	private final Table<Object, Object, ? extends Reference<?>> container = new HashTable<>();

	@Override
	public abstract void update(R reference);

	@Override
	public Table<Object, Object, ? extends Reference<?>> getContainer() {
		return container;
	}

	@Override
	public Map<Object, Supplier<? extends Reference<?>>> getCreators() {
		return creators;
	}
}
