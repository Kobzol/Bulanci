package cz.kobzol.bulanci.model;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Represents objects that can draw themselves.
 */
public interface IDrawable {
    /**
     * Draw itself on a given batch.
     * @param batch batch
     */
    void draw(Batch batch);
}
