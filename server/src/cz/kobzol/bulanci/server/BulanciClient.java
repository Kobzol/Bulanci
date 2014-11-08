package cz.kobzol.bulanci.server;

import com.esotericsoftware.kryonet.Connection;
import cz.kobzol.bulanci.connection.ConnectionSide;

/**
 * Game client.
 */
public class BulanciClient {
    private ConnectionSide connection;

    public BulanciClient(ConnectionSide connection) {
        this.connection = connection;
        this.connection.addDisconnectedListener(new ConnectionSide.Disconnected() {
            @Override
            public void disconnected() {
                System.out.println("I was f*cked out!");
            }
        });
        this.connection.addRequestListener(new ConnectionSide.Request() {
            @Override
            public Object received(Connection connection, Object object) {
                System.out.println("Dostal " + object);
                return "Vítej!";
            }
        });
        this.connection.send("Ahoj!");
    }
}
