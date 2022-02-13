package bg.sofia.uni.fmi.mjt.selfcare.server;

import bg.sofia.uni.fmi.mjt.selfcare.command.CommandCreator;
import bg.sofia.uni.fmi.mjt.selfcare.command.CommandExecutor;
import bg.sofia.uni.fmi.mjt.selfcare.utilities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRequestHandler implements Runnable {

    private Socket socket;
    private User user;
    private CommandExecutor commandExecutor;

    public ClientRequestHandler(Socket socket) {
        this.socket = socket;
        this.user = new User();
        this.commandExecutor = new CommandExecutor();
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                commandExecutor.execute(CommandCreator.create(inputLine), user);


            }

        } catch (IOException e) {
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
}
