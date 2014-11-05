package cz.kobzol.bulanci.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.awt.Dimension;

/**
 * Represents Bulanek.
 */
public class SpriteObject extends DrawableShape implements IMovable, IRotable, ICollidable {
    private Sprite sprite;

    private Vector2 direction;
    private float speed;

    private boolean dirty = true;

    public SpriteObject() {
        this.sprite = new Sprite();
        this.direction = new Vector2(0, -1);
    }

    public float getSpeed() {
        return this.speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    private void setDirty() {
        this.dirty = true;
    }

    @Override
    public void setTexture(Texture texture) {
        super.setTexture(texture);
        this.sprite = new Sprite(texture);  // TODO: pass the old sprite parameters (position, rotation, etc.) to the new sprite
        this.sprite.setOriginCenter();

        this.setDirty();
    }

    @Override
    public void setDimension(Dimension dimension) {
        super.setDimension(dimension);

        this.sprite.setBounds(this.sprite.getX(), this.sprite.getY(), (float) dimension.getWidth(), (float) dimension.getHeight());
        this.sprite.setOrigin((float) dimension.getWidth() / 2.0f, (float) dimension.getHeight() / 2.0f);

        this.setDirty();
    }

    @Override
    public void draw(Batch batch) {
        if (this.dirty) {
            batch.draw(
                    this.sprite, this.sprite.getX(), this.sprite.getY(),
                    this.sprite.getOriginX(), this.sprite.getOriginX(),
                    this.sprite.getWidth(), this.sprite.getHeight(),
                    this.sprite.getScaleX(), this.sprite.getScaleY(), this.sprite.getRotation());

            this.dirty = false;
        }
    }

    @Override
    public void move() {
        this.setPosition(this.getPosition().add(this.direction.scl(this.speed * Gdx.graphics.getDeltaTime())));

        this.setDirty();
    }

    @Override
    public float getRotation() {
        return this.direction.angle();
    }

    @Override
    public void setPosition(Vector2 position) {
        super.setPosition(position);
        this.sprite.setPosition(position.x, position.y);

        this.setDirty();
    }

    @Override
    public void rotate(float angle) {
        this.direction.setAngle(this.direction.angle() + angle);
        this.sprite.setRotation(this.direction.angle());

        this.setDirty();
    }

    @Override
    public boolean collidesWith(ICollidable collidable) {
        return this.sprite.getBoundingRectangle().overlaps(collidable.getBoundingBox());
    }

    @Override
    public Rectangle getBoundingBox() {
        return this.sprite.getBoundingRectangle();
    }
}
