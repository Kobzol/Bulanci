package cz.kobzol.bulanci.command;

public interface ICommand {
    public void execute();
    public ISignatureCommand getSignatureCommand();
}
