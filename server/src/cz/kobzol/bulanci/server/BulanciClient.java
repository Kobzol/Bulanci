package cz.kobzol.bulanci.server;

import com.esotericsoftware.kryonet.Connection;
import cz.kobzol.bulanci.connection.ConnectionSide;

/**
 * Game client.
 */
public class BulanciClient {
    private Connection connection;
    private ConnectionSide connectionSide;

    public BulanciClient(Connection connection) {
        connectionSide.setConnection(connection);
        this.connection.addListener(connectionSide);
    }
}
