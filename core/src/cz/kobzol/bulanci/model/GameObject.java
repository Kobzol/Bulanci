package cz.kobzol.bulanci.model;

/**
 * Represents game object with unique ID.
 */
public abstract class GameObject {
    private final int id;

    public GameObject(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }
}
