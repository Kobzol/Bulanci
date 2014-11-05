package cz.kobzol.bulanci.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents Bulanek.
 */
public class SpriteObject extends DrawableShape implements IMovable, IRotable, ICollidable {
    private Sprite sprite;

    private Vector2 direction;
    private float speed;

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

    @Override
    public void setTexture(Texture texture) {
        super.setTexture(texture);
        this.sprite = new Sprite(texture);  // TODO: pass the old sprite parameters (position, rotation, etc.) to the new sprite
        this.sprite.setOriginCenter();
    }

    @Override
    public void draw(Batch batch) {
        this.sprite.draw(batch);
    }

    @Override
    public void move() {
        this.setPosition(this.getPosition().add(this.direction.scl(this.speed * Gdx.graphics.getDeltaTime())));
    }

    @Override
    public float getRotation() {
        return this.direction.angle();
    }

    @Override
    public void setPosition(Vector2 position) {
        super.setPosition(position);
        this.sprite.setPosition(position.x, position.y);
    }

    @Override
    public void rotate(float angle) {
        this.direction.setAngle(this.direction.angle() + angle);
        this.sprite.setRotation(this.direction.angle());
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
