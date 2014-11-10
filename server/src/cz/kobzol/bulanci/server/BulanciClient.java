package cz.kobzol.bulanci.server;

import com.esotericsoftware.kryonet.Connection;
import cz.kobzol.bulanci.command.SetPlayerNameMessage;
import cz.kobzol.bulanci.command.SetPlayerReadyMessage;
import cz.kobzol.bulanci.connection.ConnectionSide;
import cz.kobzol.bulanci.connection.IIdentifiableMessage;
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

    public void addListener(Listener listener) {
        this.listeners.add(listener);
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
        if (message instanceof SetPlayerNameMessage) {
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

    public interface Listener {
        void onClientReady(BulanciClient client);
    }
}
