package cz.kobzol.bulanci.command.message;

import cz.kobzol.bulanci.command.util.IIdentifiableMessage;

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
