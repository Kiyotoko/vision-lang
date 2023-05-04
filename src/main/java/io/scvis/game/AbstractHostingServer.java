package io.scvis.game;

import java.util.Set;

import io.grpc.stub.StreamObserver;
import io.scvis.grpc.game.GameDescription;
import io.scvis.grpc.game.HostRequest;
import io.scvis.grpc.game.HostResponse;
import io.scvis.grpc.game.HostingGrpc.HostingImplBase;
import io.scvis.grpc.game.JoinRequest;
import io.scvis.grpc.game.JoinResponse;
import io.scvis.grpc.game.ListRequest;
import io.scvis.grpc.game.ListResponse;
import io.scvis.grpc.game.PingRequest;
import io.scvis.grpc.game.PingResponse;

public class AbstractHostingServer {

	protected AbstractHostingService service;

	public AbstractHostingService getService() {
		return service;
	}

	public static abstract class AbstractHostingService extends HostingImplBase {
		@Override
		public abstract void host(HostRequest request, StreamObserver<HostResponse> responseObserver);

		@Override
		public abstract void join(JoinRequest request, StreamObserver<JoinResponse> responseObserver);

		@Override
		public abstract void list(ListRequest request, StreamObserver<ListResponse> responseObserver);

		@Override
		public void ping(PingRequest request, StreamObserver<PingResponse> responseObserver) {
			responseObserver.onNext(PingResponse.newBuilder().setPing(System.currentTimeMillis()).build());
			responseObserver.onCompleted();
		}

		protected Set<GameDescription> descriptions;

		public Set<GameDescription> getDescriptions() {
			return descriptions;
		}
	}
}
