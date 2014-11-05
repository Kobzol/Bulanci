package cz.kobzol.bulanci.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import cz.kobzol.bulanci.model.IDrawable;
import cz.kobzol.bulanci.model.IGameObject;

/**
 * Represents player who controlls a game object.
 */
public class Player implements IDrawable {
    private final int id;
    private final String name;
    private IGameObject controlledObject;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void draw(Batch batch) {
        ((IDrawable) this.controlledObject).draw(batch);
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
        this.controlledObject = controlledObject;
    }
}
