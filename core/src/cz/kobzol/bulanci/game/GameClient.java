package cz.kobzol.bulanci.game;

import com.esotericsoftware.kryonet.Connection;
import cz.kobzol.bulanci.command.message.CreatePlayerMessage;
import cz.kobzol.bulanci.command.message.SetPlayerReadyMessage;
import cz.kobzol.bulanci.command.message.StartGameMessage;
import cz.kobzol.bulanci.command.util.IIdentifiableMessage;
import cz.kobzol.bulanci.connection.ConnectionSide;

/**
 * Game client.
 */
public class GameClient {
    private final ConnectionSide client;
    private final GameController controller;

    public GameClient(ConnectionSide client, GameController controller) {
        this.client = client;
        this.controller = controller;

        this.setEvents();
    }

    private void setEvents() {
        this.client.addRequestListener(new ConnectionSide.Request() {
            @Override
            public Object received(Connection connection, Object object) {
                if (object instanceof IIdentifiableMessage) {
                    return handleMessage((IIdentifiableMessage) object);
                }

                return null;
            }
        });
    }

    private Object handleMessage(IIdentifiableMessage message) {
        if (message instanceof CreatePlayerMessage) {
            CreatePlayerMessage msg = (CreatePlayerMessage) message;
            this.controller.createPlayer(msg.playerId);
        }
        else if (message instanceof StartGameMessage) {
            this.controller.startGame();
        }

        return true;
    }

    public ConnectionSide getConnection() {
        return this.client;
    }

    public int getID() {
        return this.client.getID();
    }

    public void setReady() {
        this.client.send(new SetPlayerReadyMessage());
    }
}
