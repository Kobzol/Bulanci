package cz.kobzol.bulanci.connection;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;
import cz.kobzol.bulanci.command.MoveCommand;
import cz.kobzol.bulanci.command.RotateCommand;
import cz.kobzol.bulanci.command.SetPlayerNameMessage;
import cz.kobzol.bulanci.command.SetPlayerReadyMessage;

/**
 * Creates Kryonet Connections with registered classes.
 */
public class KryoFactory {
    public static Client createClient() {
        Client client = new Client();
        KryoFactory.registerClasses(client);
        return client;
    }

    public static Server createServer() {
        Server server = new Server();
        KryoFactory.registerClasses(server);
        return server;
    }

    private static void registerClasses(EndPoint connection) {
        connection.getKryo().register(DataPackage.class);

        connection.getKryo().register(RotateCommand.Signature.class);
        connection.getKryo().register(MoveCommand.Signature.class);

        connection.getKryo().register(SetPlayerNameMessage.class);
        connection.getKryo().register(SetPlayerReadyMessage.class);
    }
}
