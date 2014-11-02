package cz.kobzol.bulanci.server;

import com.esotericsoftware.kryonet.Connection;

/**
 * Game client.
 */
public class BulanciClient {
    private final Connection connection;

    public BulanciClient(Connection connection) {
        this.connection = connection;
    }
}
