package cz.kobzol.bulanci.input;

import com.badlogic.gdx.Input;
import cz.kobzol.bulanci.command.MoveCommand;
import cz.kobzol.bulanci.command.RotateCommand;
import cz.kobzol.bulanci.connection.LocalCommandRouter;

/**
 * Handles player's input.
 */
public class PlayerInputHandler {
    private final LocalCommandRouter localCommandRouter;

    public PlayerInputHandler(LocalCommandRouter localCommandRouter) {
        this.localCommandRouter = localCommandRouter;
    }

    /**
     * Checks the input and generates actions according to it.
     * @param input input
     */
    public void checkInput(Input input) {
        if (input.isKeyPressed(Input.Keys.UP)) {
            this.generateMove(true);
        }
        else if (input.isKeyPressed(Input.Keys.DOWN)) {
            this.generateMove(false);
        }

        if (input.isKeyPressed(Input.Keys.RIGHT)) {
            this.generateRotation(true);
        }
        else if (input.isKeyPressed(Input.Keys.LEFT)) {
            this.generateRotation(false);
        }
    }

    private void generateRotation(boolean rotateRight) {
        RotateCommand.Signature serializedCommand = new RotateCommand.Signature(rotateRight);
        this.localCommandRouter.executeSignature(serializedCommand);
    }

    private void generateMove(boolean forward) {
        MoveCommand.Signature serializedCommand = new MoveCommand.Signature(forward);
        this.localCommandRouter.executeSignature(serializedCommand);
    }
}
