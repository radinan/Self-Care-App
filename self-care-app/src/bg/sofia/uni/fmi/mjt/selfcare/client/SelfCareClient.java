package bg.sofia.uni.fmi.mjt.selfcare.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SelfCareClient {
    private static final int SERVER_PORT = 7777;
    private static final int BUFFER_SIZE = 1024;

    private static final ByteBuffer BUFFER = ByteBuffer.allocateDirect(BUFFER_SIZE);


    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress("localhost", SERVER_PORT));
            System.out.println("Connected!");

            while (socketChannel.isConnected()) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();

//                System.out.println(message);
                BUFFER.clear();
                BUFFER.put(message.getBytes(StandardCharsets.UTF_8));
                BUFFER.flip();
                socketChannel.write(BUFFER);

                BUFFER.clear();
                socketChannel.read(BUFFER);
                BUFFER.flip();

                byte[] byteArray = new byte[BUFFER.remaining()];
                BUFFER.get(byteArray);
                String reply = new String(byteArray, StandardCharsets.UTF_8);
                System.out.println(reply);

                if (reply.equals("Disconnected from the server.")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
