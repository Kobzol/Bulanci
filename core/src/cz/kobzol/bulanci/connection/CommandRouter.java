package cz.kobzol.bulanci.connection;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import cz.kobzol.bulanci.command.CommandFactory;
import cz.kobzol.bulanci.command.ICommand;
import cz.kobzol.bulanci.command.ICommandInvoker;
import cz.kobzol.bulanci.command.ISignatureCommand;

/**
 * Temporary only for local
 */
public class CommandRouter implements ICommandInvoker {

    final int SERVER_CLIENT_ID = 0;

    CommandFactory factory;

    ICommandInvoker localInvoker;

    Client connection;

    protected int clientId = SERVER_CLIENT_ID;

    public CommandRouter(Client connection, CommandFactory factory, ICommandInvoker localInvoker) {
        this.connection = connection;
        this.factory = factory;
        this.localInvoker = localInvoker;

        connection.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof ISignatureCommand) {
                    CommandRouter.this.acceptSignature((ISignatureCommand) object);
                }
            }
        });
    }


    /**
     * If this is a local player, it must be set!
     * @param clientId
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }


    /**
     * Execute command from local player
     * @param command
     */
    public void invokeCommand(ICommand command) {
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



}
