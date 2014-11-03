package cz.kobzol.bulanci.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import cz.kobzol.bulanci.model.IDrawable;

import java.awt.*;

/**
 * Represents game map.
 */
public class Map implements IDrawable {
    private final String name;
    private Texture background;
    private final Dimension dimension;

    public Map(String name, Texture background, Dimension dimension) {
        this.name = name;
        this.background = background;
        this.dimension = dimension;
    }

    public String getName() {
        return this.name;
    }
    public Dimension getDimension() {
        return this.dimension;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(this.background, 0, 0);
    }

    @Override
    public void setTexture(Texture texture) {
        this.background = texture;
    }
}
