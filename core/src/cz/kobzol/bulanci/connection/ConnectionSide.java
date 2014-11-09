package cz.kobzol.bulanci.connection;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Communicates with server or client.
 */
public class ConnectionSide extends Listener {

    Connection connection;

    protected List<Request> requestListeners;
    protected List<Disconnected> disconnectedListeners;

    protected HashMap<String, Request> waiting;

    public ConnectionSide(Connection connection) {
        this();
        setConnection(connection);
    }

    public ConnectionSide() {
        this.disconnectedListeners = new ArrayList<Disconnected>();
        this.requestListeners = new ArrayList<Request>();
        this.waiting = new HashMap<String, Request>();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        connection.addListener(this);
    }

    public Connection getConnection() {
        return connection;
    }

    public int getID() {
        return this.connection.getID();
    }

    public void addRequestListener(Request listener) {
        this.requestListeners.add(listener);
    }

    public void addDisconnectedListener(Disconnected listener) {
        this.disconnectedListeners.add(listener);
    }

    @Override
    public void received(Connection connection, Object o) {
        if (connection != this.connection) return;

        if (o instanceof com.esotericsoftware.kryonet.FrameworkMessage) return;

        if (o instanceof DataPackage) {
            DataPackage dp = (DataPackage) o;
            if (dp.isResponse() && waiting.containsKey(dp.getPackageId())) { // is (waiting) response
                waiting.get(dp.getPackageId()).received(connection, dp.getData());
                waiting.remove(dp.getPackageId());
            } else if (dp.isRequest()) {
                for (Request request : requestListeners) {
                    Object responseObject = request.received(connection, dp.getData());
                    if (dp.isResponsible() && responseObject != null) {
                        DataPackage responsePackage = new DataPackage(responseObject, dp.getPackageId(), true);
                        ConnectionSide.this.connection.sendTCP(responsePackage);
                    }
                }
            }
        } else {
            for (Request request : requestListeners) {
                request.received(connection, o);
            }
        }
    }

    @Override
    public void disconnected(Connection connection) {
        if (connection != this.connection) return;

        for (Disconnected disconnectedListener : disconnectedListeners) {
            disconnectedListener.disconnected();
        }

        this.waiting.clear();
        this.requestListeners.clear();
        this.connection = null;
    }

    public synchronized void send(Object object, Request request) {
        DataPackage dp = new DataPackage(object, this.generateId(), false);
        this.waiting.put(dp.packageId, request);
        connection.sendTCP(dp);
    }

    public synchronized void send(Object object) {
        DataPackage dp = new DataPackage(object);
        connection.sendTCP(dp);
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public interface Request {
        public Object received(Connection connection, Object object);
    }

    public interface Disconnected {
        public void disconnected();
    }

}
