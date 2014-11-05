package cz.kobzol.bulanci.command;

public class CommandFactory {

    public ICommand build(ISerializableCommand command) throws UnknownCommand {
        if (command instanceof EchoCommand.Serializable) {
            EchoCommand.Serializable serializable = (EchoCommand.Serializable) command;
            return new EchoCommand(serializable.text);
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
