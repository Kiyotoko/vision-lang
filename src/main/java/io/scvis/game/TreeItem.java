package io.scvis.game;

import java.util.Collection;

public interface TreeItem<S, R> {

	R getOrigin();

	S getTribe();

	Collection<R> getBranches();
}
