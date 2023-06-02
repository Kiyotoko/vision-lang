package io.scvis.proto;

import javax.annotation.Nonnull;

public interface ExchangeHelper<E> {

	@Nonnull
	Tree<? extends E> getContainer();

	interface DispatchHelper<E, C> extends ExchangeHelper<E>, Corresponding<C> {

	}

	interface StoreHelper<E, R> extends ExchangeHelper<E>, Reference<R> {

	}
}
