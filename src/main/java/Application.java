import io.grpc.Server;

import java.io.IOException;

public class Application {
	private static final String HOST = "localhost";
	private static final int PORT = 9090;

	public static void main(String[] args) throws IOException, InterruptedException {
		Server server = new HelloGrpcServer(PORT).runServer();
		new HelloGrpcClient(HOST, PORT).callServer();
		server.awaitTermination();
	}
}
