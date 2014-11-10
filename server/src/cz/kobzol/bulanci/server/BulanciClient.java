package cz.kobzol.bulanci.server;

import com.esotericsoftware.kryonet.Connection;
import cz.kobzol.bulanci.command.IIdentifiableMessage;
import cz.kobzol.bulanci.command.ISignatureCommand;
import cz.kobzol.bulanci.command.message.CreatePlayerMessage;
import cz.kobzol.bulanci.command.message.SetPlayerNameMessage;
import cz.kobzol.bulanci.command.message.SetPlayerReadyMessage;
import cz.kobzol.bulanci.command.message.StartGameMessage;
import cz.kobzol.bulanci.connection.ConnectionSide;
import cz.kobzol.bulanci.player.Player;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Game client.
 */
public class BulanciClient {
    public enum ClientState {
        CONNECTED,
        NAME_SET,
        READY,
        IN_GAME
    }

    private ConnectionSide connection;
    private Player player;

    private EnumSet<ClientState> state = EnumSet.noneOf(ClientState.class);

    private List<Listener> listeners;

    public BulanciClient(ConnectionSide connection, Player player) {
        this.connection = connection;
        this.player = player;

        this.state.add(ClientState.CONNECTED);
        this.listeners = new ArrayList<Listener>();

        this.setEvents();
    }

    public int getID() {
        return this.connection.getID();
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void send(Object data) {
        this.connection.send(data);
    }

    public void sendPlayersInfo(List<BulanciClient> clients) {
        for (BulanciClient client : clients) {
            if (client != this) {
                this.send(new CreatePlayerMessage(client.getID()));
            }
        }
    }

    public void sendStartGame() {
        this.send(new StartGameMessage());
    }

    private void setEvents() {
        this.connection.addRequestListener(new ConnectionSide.Request() {
            @Override
            public Object received(Connection connection, Object object) {
                if (object instanceof IIdentifiableMessage) {
                    IIdentifiableMessage msg = (IIdentifiableMessage) object;
                    msg.setClientId(connection.getID());

                    return handleMessage(msg);
                }

                return null;
            }
        });
    }

    private Object handleMessage(IIdentifiableMessage message) {
        if (message instanceof ISignatureCommand) {
            this.notifyCommandReceived((ISignatureCommand) message);
        }
        else if (message instanceof SetPlayerNameMessage) {
            SetPlayerNameMessage msg = (SetPlayerNameMessage) message;
            this.setName(msg.name);
        }
        else if (message instanceof SetPlayerReadyMessage) {
            this.setReady();
        }

        return true;
    }

    public EnumSet<ClientState> getState() {
        return this.state;
    }

    private void setName(String name) {
        this.player.setName(name);
        this.state.add(ClientState.NAME_SET);
    }

    private void setReady() {
        this.state.add(ClientState.READY);
        this.notifyClientReady();
    }
    public boolean isReady() {
        return  this.state.contains(ClientState.READY) &&
                this.state.contains(ClientState.NAME_SET);
    }

    private void notifyClientReady() {
        for (Listener listener : this.listeners) {
            listener.onClientReady(this);
        }
    }
    private void notifyCommandReceived(ISignatureCommand signature) {
        for (Listener listener : this.listeners) {
            listener.onCommandReceived(signature);
        }
    }

    public interface Listener {
        void onClientReady(BulanciClient client);
        void onCommandReceived(ISignatureCommand signature);
    }
}
