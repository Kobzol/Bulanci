package cz.kobzol.bulanci.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Represents object's with shape and texture that can draw themselves.
 */
public abstract class DrawableShape extends Shape implements IDrawable, ITexturable {
    private Texture texture;

    @Override
    public void draw(Batch batch) {
        batch.draw(this.getTexture(), this.getPositionX(), this.getPositionY(), (float) this.getDimension().getWidth(), (float) this.getDimension().getHeight());
    }

    @Override
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public Texture getTexture() {
        return this.texture;
    }
}
