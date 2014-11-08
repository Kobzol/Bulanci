package cz.kobzol.bulanci.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import cz.kobzol.bulanci.model.IDrawable;
import cz.kobzol.bulanci.model.IGameObject;
import cz.kobzol.bulanci.model.SpriteObject;

/**
 * Represents player who controlls a game object.
 */
public class Player implements IDrawable {
    private static final float OBJECT_ROTATION = 5.0f;

    private final int id;
    private final String name;
    private SpriteObject controlledObject;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void draw(Batch batch) {
        this.controlledObject.draw(batch);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public IGameObject getControlledObject() {
        return this.controlledObject;
    }
    public void setControlledObject(IGameObject controlledObject) {
        this.controlledObject = (SpriteObject) controlledObject;
    }

    /**
     * Rotates the player's object.
     * @param rotateRight true if the rotation is to the right, false if the rotation is to the left
     */
    public void rotateObject(boolean rotateRight) {
        float rotation = Player.OBJECT_ROTATION;

        if (rotateRight) {
            rotation *= -1.0f;
        }

        this.controlledObject.rotate(rotation);
    }

    /**
     * Moves the player's object.
     * @param forward Whether the object should move forward (true) or backwards (false)
     */
    public void moveObject(boolean forward) {
        this.controlledObject.move(forward);
    }
}
