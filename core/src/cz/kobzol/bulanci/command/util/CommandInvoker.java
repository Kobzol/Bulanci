package cz.kobzol.bulanci.command.util;

/**
 * Invokes commands.
 */
public class CommandInvoker implements ICommandInvoker {
    @Override
    public void invokeCommand(ICommand command) {
        command.execute();
    }
}
