package io.scvis.game;

import java.util.Set;

import io.scvis.grpc.game.GameDescription;
import io.scvis.grpc.game.HostResponse;
import io.scvis.grpc.game.JoinResponse;
import io.scvis.grpc.game.ListResponse;
import io.scvis.grpc.game.PingResponse;

public abstract class AbstractHostingClient {

	public abstract ListResponse list();

	public abstract HostResponse host();

	public abstract JoinResponse join();

	public abstract PingResponse ping();

	protected Set<GameDescription> descriptions;

	public Set<GameDescription> getDescriptions() {
		return descriptions;
	}

	public long getPing() {
		long now = System.currentTimeMillis();
		return ping().getPing() - now;
	}
}
