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
public class ConnectionSide {

    Connection connection;

    protected List<Response> listener;

    protected HashMap<String, Response> waiting;

    public ConnectionSide(Connection connection) {
        this.listener = new ArrayList<Response>();
        this.waiting = new HashMap<String, Response>();
        this.connection = connection;
    }

    public void received(Connection connection, Object o) {
        if (o instanceof DataPackage) {
            DataPackage dp = (DataPackage) o;
            if (dp.isResponse() && waiting.containsKey(dp.getPackageId())) { // is (waiting) response
                waiting.get(dp.getPackageId()).received(connection, dp.getData());
                waiting.remove(dp.getPackageId());
            } else if (dp.isRequest()) {
                for (Response response : listener) {
                    Object responseObject = response.received(connection, dp.getData());
                    if (dp.isResponsible() && responseObject != null) {
                        DataPackage responsePackage = new DataPackage(responseObject, dp.getPackageId(), true);
                        ConnectionSide.this.connection.sendTCP(responsePackage);
                    }
                }
            }
        } else {
            for (Response response : listener) {
                response.received(connection, o);
            }
        }
    }

    public synchronized void send(Object object, Response response) {
        DataPackage dp = new DataPackage(object, this.generateId(), false);
        this.waiting.put(dp.packageId, response);
        connection.sendTCP(dp);
    }

    public synchronized void send(Object object) {
        DataPackage dp = new DataPackage(object);
        connection.sendTCP(dp);
    }

    public void destroy()
    {
        this.waiting.clear();
        this.listener.clear();
        this.connection = null;
    }

    public void addListener(Response listener) {
        this.listener.add(listener);
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public interface Response {
        public Object received(Connection connection, Object object);
    }

}
