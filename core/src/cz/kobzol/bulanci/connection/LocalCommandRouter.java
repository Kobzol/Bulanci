package cz.kobzol.bulanci.connection;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import cz.kobzol.bulanci.command.CommandFactory;
import cz.kobzol.bulanci.command.ICommand;
import cz.kobzol.bulanci.command.ICommandInvoker;
import cz.kobzol.bulanci.command.ISignatureCommand;


public class LocalCommandRouter implements ICommandInvoker {

    final int SERVER_CLIENT_ID = 0;

    CommandFactory factory;

    ICommandInvoker localInvoker;

    Client connection;

    protected Integer clientId;

    public LocalCommandRouter(CommandFactory factory, ICommandInvoker localInvoker) {
        this.factory = factory;
        this.localInvoker = localInvoker;
    }


    /**
     * If this is a local player, it must be set!
     * @param clientId
     */
    public void setClientId(Client connection, int clientId) {
        this.clientId = clientId;
        this.connection = connection;

        connection.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof ISignatureCommand) {
                    LocalCommandRouter.this.acceptSignature((ISignatureCommand) object);
                }
            }
        });
    }


    /**
     * Execute command from local player
     * @param command
     */
    public void invokeCommand(ICommand command) {
        checkConnection();
        ISignatureCommand signature = command.getSignatureCommand();
        signature.setClientId(this.clientId);
        connection.sendTCP(signature);
        this.localInvoker.invokeCommand(command);
    }


    /**
     * Execute command from local signature
     * @param signature
     */
    public void executeSignature(ISignatureCommand signature) {
        checkConnection();
        signature.setClientId(this.clientId);
        connection.sendTCP(signature);
        this.acceptSignature(signature);
    }


    /**
     * Accept signature
     * @param signature to process
     * @return is executed?
     */
    protected boolean acceptSignature(ISignatureCommand signature)
    {
        try {
            ICommand command = factory.build(signature);
            this.localInvoker.invokeCommand(command);
            return true;
        } catch (CommandFactory.UnknownCommandException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void checkConnection()
    {
        if (this.connection == null && this.clientId == null) {
            throw new Error("Please, call first setClientId()");
        }
    }

}
