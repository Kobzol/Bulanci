package cz.kobzol.bulanci.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Game server which listens for incoming connections, distributes messages amongst players and controlls the game state.
 */
public class BulanciServer {
    private final Server server;
    private final int tcpPort;
    private final int udpPort;

    private final List<BulanciClient> clients;

    public BulanciServer(int port) {
        this(port, port);
    }

    public BulanciServer(int tcpPort, int udpPort) {
        this.server = new Server();
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;

        this.clients = new ArrayList<BulanciClient>();

        this.setEvents();
    }

    /**
     * Sets the client's events.
     */
    private void setEvents() {
        this.server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                final BulanciClient client = new BulanciClient(connection);

                clients.add(client);

                connection.addListener(new Listener() {
                    @Override
                    public void disconnected(Connection connection) {
                        clients.remove(client);
                    }

                    @Override
                    public void received(Connection connection, Object data) {
                        handleMessage(client, data);
                    }
                });
            }
        });
    }

    /**
     * Handles incoming message.
     * @param client message sender
     * @param data message data
     */
    private void handleMessage(BulanciClient client, Object data) {
        System.out.println(client + ": " + data);
    }

    /**
     * Starts the server.
     * @return True if the start was successful, false otherwise
     */
    public boolean start() {
        try {
            this.server.start();
            this.server.bind(this.tcpPort, this.udpPort);

            return true;
        }
        catch(IOException exception) {
            this.stop();

            return false;
        }
    }

    /**
     * Stops the server.
     */
    public void stop() {
        this.server.stop();
    }
}
