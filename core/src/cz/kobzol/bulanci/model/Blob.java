package cz.kobzol.bulanci.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import cz.kobzol.bulanci.game.GameController;

import java.util.ArrayList;
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
        this.fireCooldown = new Cooldown(50);
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void fire() {
        if (this.fireCooldown.isReady()) {
            this.bullets.add(this.createBullet());
            this.fireCooldown.reset();
        }
    }

    @Override
    public void draw(Batch batch) {
        for (Bullet bullet : this.bullets) {
            bullet.draw(batch);
        }

        super.draw(batch);
    }

    @Override
    public void update() {
        this.fireCooldown.update();

        for (Bullet bullet : this.bullets) {
            bullet.update();
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
