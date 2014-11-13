package cz.kobzol.bulanci.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    public boolean isDirty() {
        return this.dirty;
    }
    private void setDirty() {
        this.dirty = true;
    }

    @Override
    public void setTexture(Texture texture) {
        super.setTexture(texture);

        this.sprite.setTexture(texture);
        this.sprite.setRegion(texture);
        this.setDimension(new Dimension(texture.getWidth(), texture.getHeight()));

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
    public void setPosition(Vector2 position) {
        super.setPosition(position);
        this.sprite.setPosition(position.x, position.y);

        this.setDirty();
    }

    @Override
    public void draw(Batch batch) {
            batch.draw(
                    this.sprite, this.sprite.getX() - this.sprite.getOriginX(), this.sprite.getY()  - this.sprite.getOriginY(),
                    this.sprite.getOriginX(), this.sprite.getOriginY(),
                    this.sprite.getWidth(), this.sprite.getHeight(),
                    this.sprite.getScaleX(), this.sprite.getScaleY(), this.sprite.getRotation());

        this.dirty = false;
    }


    public void drawShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(this.getBoundingBox().x, this.getBoundingBox().y, this.getBoundingBox().width, this.getBoundingBox().height);
    }

    @Override
    public void move(boolean forward) {
        float speed = this.speed;

        if (!forward) {
            speed *= -1.0f;
        }

        Vector2 move_vector = new Vector2(this.direction);

        this.setPosition(this.getPosition().add(move_vector.scl(speed)));

        this.setDirty();
    }

    @Override
    public float getRotation() {
        return this.direction.angle();
    }

    @Override
    public void setRotation(float angle) {
        this.direction.setAngle(angle);
        this.sprite.setRotation(this.direction.angle() - 90);

        this.setDirty();
    }

    @Override
    public Vector2 getDirection() {
        return new Vector2(this.direction);
    }

    @Override
    public void setDirection(Vector2 direction) {
        this.direction.set(direction);
        this.setRotation(this.direction.angle());
    }

    @Override
    public void rotate(float angle) {
        this.setRotation(this.getRotation() + angle);
    }

    @Override
    public boolean collidesWith(ICollidable collidable) {
        return this.getBoundingBox().overlaps(collidable.getBoundingBox());
    }

    @Override
    public Rectangle getBoundingBox() {
        Rectangle rect = this.sprite.getBoundingRectangle();
        rect.x -= this.getDimension().getWidth() / 2.0f;
        rect.y -= this.getDimension().getHeight() / 2.0f;

        return rect;
    }
}
