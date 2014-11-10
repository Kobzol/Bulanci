package cz.kobzol.bulanci.command;

import cz.kobzol.bulanci.connection.IIdentifiableMessage;

/**
 * Represents net message.
 */
public abstract class Message implements IIdentifiableMessage {
    private int clientId;

    @Override
    public int getClientId() {
        return this.clientId;
    }

    @Override
    public void setClientId(int id) {
        this.clientId = id;
    }
}
