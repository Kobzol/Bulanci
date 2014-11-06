package cz.kobzol.bulanci.command;

import cz.kobzol.bulanci.player.Player;

/**
 * Rotate's the player's object.
 */
public class RotateCommand implements ICommand {
    public static class Signature extends SignatureCommand {

        public final boolean rotateRight;

        public Signature(boolean rotateRight) {
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
    public ISignatureCommand getSignatureCommand() {
        return new Signature(this.rotateRight);
    }
}
