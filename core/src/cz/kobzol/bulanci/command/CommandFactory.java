package cz.kobzol.bulanci.command;

import cz.kobzol.bulanci.map.Level;

public class CommandFactory {

    private final Level level;

    public CommandFactory(Level level){
        this.level = level;
    }

    public ICommand build(ISignatureCommand command) throws UnknownCommand {
        if (command instanceof EchoCommand.Signature) {
            EchoCommand.Signature serializable = (EchoCommand.Signature) command;
            return new EchoCommand(serializable.text);

        } else if (command instanceof RotateCommand.Signature) {
            RotateCommand.Signature serializable = (RotateCommand.Signature) command;
            return new RotateCommand(level.getPlayerById(this.localPlayerId), serializable.rotateRight);
        }

        throw new UnknownCommand(command);
    }

    public class UnknownCommand extends Exception {
        private ISignatureCommand command;

        public UnknownCommand(ISignatureCommand command) {
            super();
            this.command = command;
        }

        public ISignatureCommand getCommand() {
            return command;
        }
    }

}
