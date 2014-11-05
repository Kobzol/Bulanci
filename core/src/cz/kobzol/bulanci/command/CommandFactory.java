package cz.kobzol.bulanci.command;

import cz.kobzol.bulanci.map.Level;

public class CommandFactory {
    private final Level level;
    private final int localPlayerId;

    public CommandFactory(Level level, int localPlayerId){
        this.level = level;
        this.localPlayerId = localPlayerId;
    }

    public ICommand build(ISerializableCommand command) throws UnknownCommand {
        if (command instanceof EchoCommand.Serializable) {
            EchoCommand.Serializable serializable = (EchoCommand.Serializable) command;
            return new EchoCommand(serializable.text);
        }
        else if (command instanceof RotateCommand.Serializable) {
            RotateCommand.Serializable serializable = (RotateCommand.Serializable) command;
            return new RotateCommand(level.getPlayerById(this.localPlayerId), serializable.rotateRight);
        }

        throw new UnknownCommand(command);
    }

    public class UnknownCommand extends Exception {
        private ISerializableCommand command;

        public UnknownCommand(ISerializableCommand command) {
            super();
            this.command = command;
        }

        public ISerializableCommand getCommand() {
            return command;
        }
    }

}
