package cz.kobzol.bulanci.model;

import com.badlogic.gdx.math.Vector2;

import java.awt.Dimension;

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

    /**
     * Set's the object's dimension.
     * @param dimension dimension
     */
    void setDimension(Dimension dimension);

    /**
     * Get's the object's dimension.
     * @return dimension of the object
     */
    Dimension getDimension();
}
