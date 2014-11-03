package cz.kobzol.bulanci.model;

import java.awt.*;

/**
 * Represents objects that can move.
 */
public interface IMovable {
    /**
     * Moves the object in it's direction with it's speed.
     */
    void move();

    /**
     * Sets the object's position.
     * @param position position
     */
    void setPosition(Point position);
}
