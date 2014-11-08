package cz.kobzol.bulanci.command;

import java.io.Serializable;

public interface ISignatureCommand extends Serializable {
    public int getClientId();
    public void setClientId(int id);
}
