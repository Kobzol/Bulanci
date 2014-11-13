package cz.kobzol.bulanci.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import cz.kobzol.bulanci.game.GameController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Blob that shoots bullets.
 */
public class Blob extends SpriteObject implements IUpdatable {
    private GameController gameController;

    private List<Bullet> bullets;
    private Cooldown fireCooldown;

    public Blob() {
        this.bullets = new ArrayList<Bullet>();
        this.fireCooldown = new Cooldown(20);
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public synchronized void fire() {
        if (this.fireCooldown.isReady()) {
            this.bullets.add(this.createBullet());
            this.fireCooldown.reset();
        }
    }

    @Override
    public synchronized void draw(Batch batch) {
        for (Bullet bullet : this.bullets) {
            bullet.draw(batch);
        }

        super.draw(batch);
    }

    @Override
    public void drawShape(ShapeRenderer shapeRenderer) {
        for (Bullet bullet : this.bullets) {
            bullet.drawShape(shapeRenderer);
        }

        super.drawShape(shapeRenderer);
    }

    @Override
    public synchronized void update() {
        this.fireCooldown.update();

        for (Iterator<Bullet> it = this.bullets.iterator(); it.hasNext();) {
            Bullet bullet = it.next();

            if (this.game.collidesWithWalls(bullet)) {
                it.remove();
            }
            else bullet.update();
        }
    }

    @Override
    public void move(boolean forward) {
        float x = this.getPositionX();
        float y = this.getPositionY();

        super.move(forward);

        if (this.game.collidesWithWalls(this)) {
            this.getPosition().set(x, y);
        }
    }

    private Bullet createBullet() {
        Bullet bullet = new Bullet();
        bullet.setPosition(this.getPosition());
        bullet.setDirection(this.getDirection());
        bullet.setTexture(this.gameController.getAssetManager().get("bullet.png", Texture.class));
        bullet.setSpeed(20);

        return bullet;
    }
}
