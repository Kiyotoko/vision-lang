package io.scvis.proto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.scvis.proto.ExchangeHelper.DispatchHelper;

public abstract class AbstractExchangeHelper<R> implements DispatchHelper<Corresponding<?>, R> {
	private final Map<Class<?>, Collection<Corresponding<?>>> container = new HashMap<>();

	@Override
	public abstract R associated();

	@Override
	public void clean() {
		for (Collection<Corresponding<?>> collection : container.values()) {
			collection.clear();
		}
	}

	@Override
	public void push(Collection<Corresponding<?>> collection, Corresponding<?> element) {
		collection.add(element);
	}

	public Map<Class<?>, Collection<Corresponding<?>>> getContainer() {
		return container;
	}
}
