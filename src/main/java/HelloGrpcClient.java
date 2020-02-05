import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.baeldung.grpc.HelloRequest;
import org.baeldung.grpc.HelloResponse;
import org.baeldung.grpc.HelloServiceGrpc;

import java.time.Duration;
import java.time.Instant;

public class HelloGrpcClient {
	private final String host;
	private final int port;

	public HelloGrpcClient (String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void callServer() {
		Instant start = Instant.now();
		ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.build();

		HelloServiceGrpc.HelloServiceBlockingStub stub
				= HelloServiceGrpc.newBlockingStub(channel);
		System.out.println(String.format("setup take: %d milis", Duration.between(start, Instant.now()).toMillis()));

		for (int i = 0; i < 10; i++) {
			start = Instant.now();
			HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
					.setFirstName("user")
					.setLastName("" + i)
					.build());

			System.out.println(String.format("%s take %d milis", helloResponse.getGreeting(), Duration.between(start, Instant.now()).toMillis()));
		}
		channel.shutdown();
	}
}
