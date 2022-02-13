package bg.sofia.uni.fmi.mjt.selfcare.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SelfCareServer {
    private static final int SERVER_PORT = 4444;
    private static final int MAX_EXECUTOR_THREADS = 10;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_EXECUTOR_THREADS);

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Server started and listening.");

            Socket clientSocket;

            while (true) {
                clientSocket = serverSocket.accept();

                System.out.println("Accepted connection from client: " + clientSocket.getInetAddress());

                ClientRequestHandler clientRequestHandler = new ClientRequestHandler(clientSocket);

                executor.execute(clientRequestHandler);
            }
        } catch (IOException e) {
            e.printStackTrace(); //change
        }
    }
}
