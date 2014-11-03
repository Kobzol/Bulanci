package cz.kobzol.bulanci.model;

import com.badlogic.gdx.math.Vector2;

import java.awt.Dimension;

/**
 * Represents game shape with location and dimension.
 */
public abstract class Shape extends GameObject implements IPositionable  {
    private final Dimension dimension;
    private final Vector2 position;

    public Shape(int id) {
        super(id);

        this.dimension = new Dimension();
        this.position = new Vector2(0, 0);
    }

    @Override
    public Dimension getDimension() {
        return this.dimension;
    }

    @Override
    public void setDimension(Dimension dimension) {
        this.dimension.setSize(dimension.getSize());
    }

    @Override
    public float getPositionX() {
        return this.position.x;
    }

    @Override
    public float getPositionY() {
        return this.position.y;
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position.set(position);
    }
}
