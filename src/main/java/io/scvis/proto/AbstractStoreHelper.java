package io.scvis.proto;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.scvis.proto.ExchangeHelper.StoreHelper;

/**
 * @author karlz
 */
public abstract class AbstractStoreHelper<R> implements StoreHelper<Reference<?>, R> {
	private final Map<String, Supplier<? extends Reference<?>>> creators = new HashMap<>();
	private final Map<String, Map<String, ? extends Reference<?>>> container = new HashMap<>();

	@Override
	public abstract void update(R reference);

	@Override
	public Map<String, Map<String, ? extends Reference<?>>> getContainer() {
		return container;
	}

	@Override
	public Map<String, Supplier<? extends Reference<?>>> getCreators() {
		return creators;
	}

	@Override
	public Reference<?> save(Map<String, Reference<?>> map, String type, String id) {
		Reference<?> reference = map.get(id);
		if ((reference = map.get(id)) == null) {
			Reference<?> newValue;
			if ((newValue = getCreators().get(type).get()) != null) {
				map.put(id, newValue);
				return newValue;
			}
		}
		return reference;
	}
}
