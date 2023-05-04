package io.scvis.game;

import java.util.Collection;

public interface NetworkItem<S, R> {

	S getNode();

	Collection<R> getConnections();
}
