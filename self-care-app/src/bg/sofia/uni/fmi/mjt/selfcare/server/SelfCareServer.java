package bg.sofia.uni.fmi.mjt.selfcare.server;

import bg.sofia.uni.fmi.mjt.selfcare.command.CommandCreator;
import bg.sofia.uni.fmi.mjt.selfcare.command.CommandExecutor;
import bg.sofia.uni.fmi.mjt.selfcare.exceptions.*;
import bg.sofia.uni.fmi.mjt.selfcare.storage.FileEditor;
import bg.sofia.uni.fmi.mjt.selfcare.entities.User;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SelfCareServer {
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = 1024;

    private final int port;
    private ByteBuffer buffer;
    private Selector selector;
    private boolean isServerUp;

    private final CommandExecutor commandExecutor;

    private final Map<Channel, User> channelsToUsers;


    public SelfCareServer(int port) {
        this.port = port;
        this.commandExecutor = new CommandExecutor(null, new FileEditor());
        this.channelsToUsers = new HashMap<>();
    }

    public void start() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            this.selector = Selector.open();
            configureServerSocketChannel(serverSocketChannel, selector);
            this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
            isServerUp = true;

            while (isServerUp) {
                try {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) {
                        continue;
                    }

                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();

                        if (key.isReadable()) {
                            SocketChannel clientChannel = (SocketChannel) key.channel();
                            String clientInput = getClientInput(clientChannel);

                            try {
                                String serverReply = commandExecutor.execute(CommandCreator.create(clientInput),
                                        channelsToUsers.get(clientChannel));

                                if (serverReply.equals("Disconnected.")) {
                                    writeClientOutput(clientChannel, "Disconnected from the server.");
                                    clientChannel.close();
                                    clientChannel.keyFor(selector).cancel();

                                    channelsToUsers.remove(clientChannel);
                                } else {
                                    writeClientOutput(clientChannel, serverReply);
                                }
                            } catch (RestServerException | FileEditorException e) {
                                writeClientOutput(clientChannel, "Service unavailable.");
                            } catch (InvalidArgumentException | UnauthorizedException | UnknownCommandException e) {
                                writeClientOutput(clientChannel, e.getMessage());
                            }
                        } else if (key.isAcceptable()) {
                            accept(selector, key);
                        }

                        keyIterator.remove();
                    }
                } catch (IOException e) {
                    System.out.println("Error occurred while processing client request: " + e.getMessage()); //rethrow
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to start server", e);
        }
    }

    public void stop() {
        isServerUp = false;
        if (selector.isOpen()) {
            selector.wakeup();
        }
    }

    private void configureServerSocketChannel(ServerSocketChannel serverSocketChannel,
                                              Selector selector) throws IOException {
        serverSocketChannel.bind(new InetSocketAddress(SERVER_HOST, port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    private String getClientInput(SocketChannel clientChannel) throws IOException {
        buffer.clear();

        int readBytes = clientChannel.read(buffer);
        if (readBytes < 0) {
            clientChannel.close();
            return null;
        }

        buffer.flip();

        byte[] clientInputBytes = new byte[buffer.remaining()];
        buffer.get(clientInputBytes);

        return new String(clientInputBytes, StandardCharsets.UTF_8);
    }

    private void writeClientOutput(SocketChannel clientChannel, String serverReply) throws IOException {
        buffer.clear();
        buffer.put(serverReply.getBytes(StandardCharsets.UTF_8));
        buffer.flip();

        clientChannel.write(buffer);
    }

    private void accept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = serverChannel.accept();

        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);

        updateChannelsToUsers(accept);
    }

    private void updateChannelsToUsers(Channel channel) {
        if (!channelsToUsers.containsKey(channel)) {
            channelsToUsers.put(channel, new User());
        }
    }
}
