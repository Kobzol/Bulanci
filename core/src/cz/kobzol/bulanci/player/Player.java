package cz.kobzol.bulanci.player;

import cz.kobzol.bulanci.model.IGameObject;

/**
 * Represents player who controlls a game object.
 */
public class Player {
    private final int id;
    private final String name;
    private IGameObject controlledObject;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
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
