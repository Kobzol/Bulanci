package cz.kobzol.bulanci.command.util;

public interface ICommand {
    public void execute();
    public ISignatureCommand getSignatureCommand();
}
