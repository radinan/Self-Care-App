package bg.sofia.uni.fmi.mjt.selfcare.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SelfCareClient {
    private static final int SERVER_PORT = 4444;

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", SERVER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server");

            while (true) {
                System.out.println("Enter message");
                String message = scanner.nextLine();

                if ("quit".equals(message)) {
                    break;
                }

//                System.out.println("Sending message <" + message + "> to the server.");
//
//                writer.println(message);
//
//                String reply = reader.readLine();
//                System.out.println("Server replied <" + reply + ">.");
            }

        } catch (IOException e) {
            throw new RuntimeException("Problem with network communication", e);
        }
    }
}
