import io.grpc.Server;
import io.grpc.ServerBuilder;
import service.HelloServiceImpl;

import java.io.IOException;

public class HelloGrpcServer {
	private final int port;

	public HelloGrpcServer(int port) {
		this.port = port;
	}

	public Server runServer() throws IOException {
		Server server = ServerBuilder
				.forPort(port)
				.addService(new HelloServiceImpl()).build();
		server.start();
		System.out.println("Listening in port: " + port);
		return server;
	}
}
