package cz.kobzol.bulanci.command;

import cz.kobzol.bulanci.command.util.ICommand;
import cz.kobzol.bulanci.command.util.ISignatureCommand;
import cz.kobzol.bulanci.player.Player;

/**
 * Fire's the player's blob gun.
 */
public class FireCommand implements ICommand {
    public static class Signature extends SignatureCommand {
        public Signature() {}
    }

    private final Player player;

    public FireCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        this.player.fire();
    }

    @Override
    public ISignatureCommand getSignatureCommand() {
        return new Signature();
    }
}
