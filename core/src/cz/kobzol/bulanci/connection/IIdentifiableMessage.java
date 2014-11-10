package cz.kobzol.bulanci.connection;

/**
 * Represents message with sender ID.
 */
public interface IIdentifiableMessage {
    void setClientId(int clientId);
    int getClientId();
}
