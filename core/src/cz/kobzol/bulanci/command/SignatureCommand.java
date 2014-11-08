package cz.kobzol.bulanci.command;


abstract public class SignatureCommand implements ISignatureCommand {

    private int clientId;

    @Override
    public int getClientId() {
        return this.clientId;
    }

    @Override
    public void setClientId(int id) {
        this.clientId = id;
    }
}
