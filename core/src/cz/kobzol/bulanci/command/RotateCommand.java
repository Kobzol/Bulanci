package cz.kobzol.bulanci.command;

import cz.kobzol.bulanci.player.Player;

/**
 * Rotate's the player's object.
 */
public class RotateCommand implements ICommand {
    public static class Serializable extends SerializableCommand {
        public final boolean rotateRight;

        public Serializable(boolean rotateRight) {
            this.rotateRight = rotateRight;
        }
    }

    private final Player player;
    private final boolean rotateRight;

    public RotateCommand(Player player, boolean rotateRight) {
        this.player = player;
        this.rotateRight = rotateRight;
    }

    @Override
    public void execute() {
        this.player.rotateObject(this.rotateRight);
    }

    @Override
    public ISerializableCommand getSeriliaziableCommand() {
        return new Serializable(this.rotateRight);
    }
}
