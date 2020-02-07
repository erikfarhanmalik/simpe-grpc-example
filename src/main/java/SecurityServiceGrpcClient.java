import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.baeldung.grpc.*;

import java.time.Duration;
import java.time.Instant;

public class SecurityServiceGrpcClient {
	private final String host;
	private final int port;

	public SecurityServiceGrpcClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void callServer() {
		Instant start = Instant.now();
		ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.build();

		SecurityServiceGrpc.SecurityServiceBlockingStub stub = SecurityServiceGrpc.newBlockingStub(channel);
		System.out.println(String.format("setup take: %d milis", Duration.between(start, Instant.now()).toMillis()));

		for (int i = 0; i < 5; i++) {
			DecodeTokenResponse decodeTokenResponse = stub.decode(DecodeTokenRequest.newBuilder()
					.setEncodedToken("--- the encoded token ---")
					.build());

			System.out.println(String.format("%s take %d milis", decodeTokenResponse.getDecodedToken(), Duration.between(start, Instant.now()).toMillis()));
		}
		channel.shutdown();
	}

	public static void main(String[] args) {
		new SecurityServiceGrpcClient("localhost", 54496).callServer();
	}
}
