package cz.kobzol.bulanci.command;

import cz.kobzol.bulanci.game.Game;

public class CommandFactory {

    private final Game game;

    public CommandFactory(Game game){
        this.game = game;
    }

    public ICommand build(ISignatureCommand command) throws UnknownCommandException {
        if (command instanceof EchoCommand.Signature) {
            EchoCommand.Signature serializable = (EchoCommand.Signature) command;
            return new EchoCommand(serializable.text);

        } else if (command instanceof RotateCommand.Signature) {
            RotateCommand.Signature serializable = (RotateCommand.Signature) command;
            return new RotateCommand(game.getLevel().getPlayerById(serializable.getClientId()), serializable.rotateRight);
        } else if (command instanceof MoveCommand.Signature) {
            MoveCommand.Signature serializable = (MoveCommand.Signature) command;
            return new MoveCommand(game.getLevel().getPlayerById(serializable.getClientId()), serializable.forward);
        }

        throw new UnknownCommandException(command);
    }

    public class UnknownCommandException extends Exception {
        private ISignatureCommand command;

        public UnknownCommandException(ISignatureCommand command) {
            super();
            this.command = command;
        }

        public ISignatureCommand getCommand() {
            return command;
        }
    }

}
