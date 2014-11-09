package cz.kobzol.bulanci.server;

import cz.kobzol.bulanci.connection.ConnectionSide;

import java.util.ArrayList;
import java.util.List;

/**
 * Game client.
 */
public class BulanciClient {
    public enum ClientState {
        CONNECTED,
        READY,
        IN_GAME
    }

    private ConnectionSide connection;
    private ClientState state;

    private List<Listener> listeners;

    public BulanciClient(ConnectionSide connection) {
        this.connection = connection;
        this.state = ClientState.CONNECTED;
        this.listeners = new ArrayList<Listener>();

        this.setEvents();
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    private void setEvents() {
        this.connection.addDisconnectedListener(new ConnectionSide.Disconnected() {
            @Override
            public void disconnected() {
                System.out.println("I was f*cked out! " + this);
            }
        });
    }

    public ClientState getState() {
        return this.state;
    }

    public void setReady() {
        this.state = ClientState.READY;
        this.notifyClientReady();
    }
    public boolean isReady() {
        return this.state == ClientState.READY;
    }

    public void setInGame() {
        this.state = ClientState.IN_GAME;
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
