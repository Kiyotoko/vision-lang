package io.scvis.entity;

import java.util.Collection;

import javax.annotation.Nonnull;

public interface TreeItem<S, R> {

	@Nonnull
	R getOrigin();

	@Nonnull
	S getTribe();

	@Nonnull
	Collection<R> getBranches();
}
