package cz.kobzol.bulanci.connection;

/**
 * Dummy connection for single player testing.
 */
public class DummyConnectionSide extends ConnectionSide {

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public synchronized void send(Object object, Request request) {

    }

    @Override
    public synchronized void send(Object object) {

    }

    @Override
    public synchronized void sendUDP(Object object) {

    }
}
