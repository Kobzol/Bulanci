package cz.kobzol.bulanci.model;

/**
 * Bullet.
 */
public class Bullet extends SpriteObject implements IUpdatable {
    @Override
    public void update() {
        this.move(true);
    }
}
