package cz.kobzol.bulanci.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import cz.kobzol.bulanci.connection.ConnectionSide;
import cz.kobzol.bulanci.connection.DataPackage;

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
        this.server.getKryo().register(DataPackage.class);

        this.clients = new ArrayList<BulanciClient>();

        this.server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                BulanciClient client = new BulanciClient(new ConnectionSide(connection));
                clients.add(client);
            }
        });
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
