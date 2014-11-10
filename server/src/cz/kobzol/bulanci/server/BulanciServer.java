package cz.kobzol.bulanci.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import cz.kobzol.bulanci.connection.ConnectionSide;
import cz.kobzol.bulanci.connection.KryoFactory;
import cz.kobzol.bulanci.game.Game;
import cz.kobzol.bulanci.map.Level;
import cz.kobzol.bulanci.map.MapLoader;
import cz.kobzol.bulanci.player.Player;

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
    private Game game;

    public BulanciServer(int port) {
        this(port, port);
    }

    public BulanciServer(int tcpPort, int udpPort) {
        this.server = KryoFactory.createServer();
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;

        this.clients = new ArrayList<BulanciClient>();
        this.server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                createClient(connection);
            }
        });

        this.game = this.createGame();
    }

    public List<BulanciClient> getClients() {
        return this.clients;
    }

    private Game createGame() {
        Level level = new MapLoader(null).parseLevel(cz.kobzol.bulanci.util.Files.readFile("map_proposal.xml"));
        return new Game(level);
    }

    private void createClient(Connection connection) {
        Player player = new Player(connection.getID());
        player.setControlledObject(this.game.getLevel().getObjectByKey("player" + connection.getID()));
        this.game.getLevel().addPlayer(player);

        BulanciClient client = new BulanciClient(new ConnectionSide(connection), player);
        clients.add(client);

        client.addListener(new BulanciClient.Listener() {
            @Override
            public void onClientReady(BulanciClient client) {
                checkClientStates();
            }
        });
    }

    private void checkClientStates() {
        boolean allClientsReady = true;

        for (BulanciClient client : this.clients) {
            if (!client.isReady())
            {
                allClientsReady = false;
                break;
            }
        }

        if (this.clients.size() > 1 && allClientsReady) {
            this.startGame();
        }
    }

    private void startGame() {

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
