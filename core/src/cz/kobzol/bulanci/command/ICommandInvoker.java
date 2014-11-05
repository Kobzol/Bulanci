package cz.kobzol.bulanci.command;

/**
 * Invokes commands.
 */
public interface ICommandInvoker {
    void invokeCommand(ICommand command);
}
