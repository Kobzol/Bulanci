package cz.kobzol.bulanci.model;

/**
 * Represents game object with unique ID.
 */
public abstract class GameObject implements IGameObject {

    private static int id_counter = 0;

    private final int id;
    private String key;

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
}
