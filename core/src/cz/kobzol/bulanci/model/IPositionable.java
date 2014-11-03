package cz.kobzol.bulanci.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents objects with position.
 */
public interface IPositionable {
    /**
     * Gets the object's X-coordinate value.
     * @return X-coordinate
     */
    float getPositionX();

    /**
     * Gets the object's Y-coordinate value.
     * @return Y-coordinate
     */
    float getPositionY();

    /**
     * Sets the object's position.
     * @param position position
     */
    void setPosition(Vector2 position);
}
