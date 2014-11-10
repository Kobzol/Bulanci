package cz.kobzol.bulanci.command;

/**
 * Message that set's the player's name.
 */
public class SetPlayerNameMessage extends Message {
    public String name;

    public SetPlayerNameMessage() {

    }
    public SetPlayerNameMessage(String name) {
        this.name = name;
    }
}
