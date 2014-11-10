package cz.kobzol.bulanci.command;

import cz.kobzol.bulanci.connection.IIdentifiableMessage;

import java.io.Serializable;

public interface ISignatureCommand extends Serializable, IIdentifiableMessage {
    public int getClientId();
    public void setClientId(int id);
}
