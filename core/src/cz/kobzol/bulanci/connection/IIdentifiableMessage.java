package cz.kobzol.bulanci.connection;

/**
 * Represents message with sender ID.
 */
public interface IIdentifiableMessage {
    public void setClientId(int clientId);
    public int getClientId();
}
