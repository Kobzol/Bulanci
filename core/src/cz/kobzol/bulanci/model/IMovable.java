package cz.kobzol.bulanci.model;

/**
 * Represents objects that can move.
 */
public interface IMovable {
    /**
     * Moves the object in it's direction with it's speed.
     * @param forward True, if the object should move forward, false, if the object should move backwards
     */
    void move(boolean forward);
}
