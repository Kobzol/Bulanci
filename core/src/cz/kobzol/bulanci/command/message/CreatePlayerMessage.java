package cz.kobzol.bulanci.command.message;

/**
 * Creates a remote player on the client.
 */
public class CreatePlayerMessage extends Message {
    public int playerId;

    public CreatePlayerMessage() {

    }
    public CreatePlayerMessage(int playerId) {
        this.playerId = playerId;
    }
}
