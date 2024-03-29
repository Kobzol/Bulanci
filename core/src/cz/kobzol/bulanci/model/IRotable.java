package cz.kobzol.bulanci.model;

/**
 * Represents objects that can rotate.
 */
public interface IRotable {
    /**
     * Returns the object's rotation in degrees.
     * @return rotation in degrees
     */
    float getRotation();

    /**
     * Set's the object's rotation in degrees.
     * @param angle angle in degrees
     */
    void setRotation(float angle);

    /**
     * Rotates the object with the given angle in degrees.
     * @param angle rotation in degrees
     */
    void rotate(float angle);
}
