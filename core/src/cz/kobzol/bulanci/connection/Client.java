package cz.kobzol.bulanci.connection;

import com.esotericsoftware.kryonet.Listener;
import cz.kobzol.bulanci.command.ICommand;
import cz.kobzol.bulanci.command.ISignatureCommand;

/**
 * Communicates with server.
 */
public class Client {
    private int clientID = 1;   // TODO

    public void sendCommand(ICommand command) {
        this.sendCommandSignature(command.getSignatureCommand());
    }

    public void sendCommandSignature(ISignatureCommand signature) {
        // TODO
    }

    public void addListener(Listener listener) {
        // TODO
    }
}
