package cz.kobzol.bulanci.model;

/**
 * Represents game object with unique ID.
 */
public abstract class GameObject {

    private final String UNNAMED_KEY_PREFIX = "gameobject_";

    private static int id_counter = 0;

    private final int id;
    private String key;

    public GameObject() {
        this.id = GameObject.id_counter++;
        this.key = UNNAMED_KEY_PREFIX + this.id;
    }

    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public int getID() {
        return this.id;
    }
}
