package io.scvis.proto;

public interface ExchangeHelper<E> {

	interface DispatchHelper<E, C> extends ExchangeHelper<E>, Corresponding<C> {

	}

	interface StoreHelper<E, R> extends ExchangeHelper<E>, Reference<R> {

	}
}
