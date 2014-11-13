package cz.kobzol.bulanci.model;

import cz.kobzol.bulanci.game.Game;

/**
 * Represents game object with unique ID.
 */
public abstract class GameObject implements IGameObject {
    private static int id_counter = 0;

    private final int id;
    private String key;

    protected Game game;

    public GameObject() {
        this.id = GameObject.id_counter++;
    }

    public GameObject(String key) {
        this();
        this.key = key;
    }

    final public int getId() {
        return this.id;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
