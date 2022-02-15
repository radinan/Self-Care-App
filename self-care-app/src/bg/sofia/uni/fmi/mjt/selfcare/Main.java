package bg.sofia.uni.fmi.mjt.selfcare;

import bg.sofia.uni.fmi.mjt.selfcare.command.CommandExecutor;
import bg.sofia.uni.fmi.mjt.selfcare.server.SelfCareServer;
import bg.sofia.uni.fmi.mjt.selfcare.storage.FileEditor;

public class Main {
    public static void main(String[] args) {
        final int port = 7777;
        SelfCareServer server = new SelfCareServer(port);
        server.start();
    }
}
