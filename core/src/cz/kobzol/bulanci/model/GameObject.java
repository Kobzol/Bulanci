package cz.kobzol.bulanci.model;

/**
 * Represents game object with unique ID.
 */
public abstract class GameObject {
    private static int id_counter = 0;

    private final int id;
    private String key;

    public GameObject() {
        this.id = GameObject.id_counter++;
        this.key = "gameobject_" + this.id;
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
