package cz.kobzol.bulanci.command;

import cz.kobzol.bulanci.player.Player;

/**
 * Move's the player's object.
 */
public class MoveCommand implements ICommand {
    public static class Signature extends SignatureCommand {
        public final boolean forward;

        public Signature(boolean forward) {
            this.forward = forward;
        }
    }

    private final Player player;
    private final boolean forward;

    public MoveCommand(Player player, boolean forward) {
        this.player = player;
        this.forward = forward;
    }

    @Override
    public void execute() {
        this.player.moveObject(this.forward);
    }

    @Override
    public ISignatureCommand getSignatureCommand() {
        return new Signature(this.forward);
    }
}
