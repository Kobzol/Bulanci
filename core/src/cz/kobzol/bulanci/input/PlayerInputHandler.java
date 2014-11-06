package cz.kobzol.bulanci.input;

import com.badlogic.gdx.Input;
import cz.kobzol.bulanci.command.CommandFactory;
import cz.kobzol.bulanci.command.ICommandInvoker;
import cz.kobzol.bulanci.command.RotateCommand;

/**
 * Handles player's input.
 */
public class PlayerInputHandler {
    private final CommandFactory commandFactory;
    private final ICommandInvoker commandInvoker;

    public PlayerInputHandler(CommandFactory commandFactory, ICommandInvoker commandInvoker) {
        this.commandFactory = commandFactory;
        this.commandInvoker = commandInvoker;
    }

    /**
     * Checks the input and generates actions according to it.
     * @param input input
     */
    public void checkInput(Input input) {
        if (input.isKeyPressed(Input.Keys.RIGHT)) {
            this.generateRotation(true);
        }
        else if (input.isKeyPressed(Input.Keys.LEFT)) {
            this.generateRotation(false);
        }
    }

    private void generateRotation(boolean rotateRight) {
        RotateCommand.Signature serializedCommand = new RotateCommand.Signature(rotateRight);

        try {
            RotateCommand command = (RotateCommand) this.commandFactory.build(serializedCommand);
            this.commandInvoker.invokeCommand(command);
        }
        catch (CommandFactory.UnknownCommand unknownCommandException) {
            unknownCommandException.printStackTrace();
        }
    }
}
