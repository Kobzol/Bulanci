package cz.kobzol.bulanci.command;

public interface ISerializableCommand {
    public int getClientId();
    public void setClientId(int id);
}
