package cz.kobzol.bulanci.command;

/**
 * Represents message with sender ID.
 */
public interface IIdentifiableMessage {
    public void setClientId(int clientId);
    public int getClientId();
}
