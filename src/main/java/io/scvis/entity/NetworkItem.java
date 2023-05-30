package io.scvis.entity;

import java.util.Collection;

import javax.annotation.Nonnull;

public interface NetworkItem<S, R> {

	@Nonnull
	S getNode();

	@Nonnull
	Collection<R> getConnections();
}
