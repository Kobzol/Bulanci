package cz.kobzol.bulanci.game;

import cz.kobzol.bulanci.map.Level;
import cz.kobzol.bulanci.map.Map;
import cz.kobzol.bulanci.model.GameObject;
import cz.kobzol.bulanci.model.ICollidable;
import cz.kobzol.bulanci.model.IUpdatable;
import cz.kobzol.bulanci.model.Wall;
import cz.kobzol.bulanci.player.Player;

/**
 * Represents the game.
 */
public class Game {
    public enum GameState {
        CREATED,
        RUNNING
    }

    private Level level;
    private GameState state;

    public Game(Level level) {
        this.level = level;
        this.state = GameState.CREATED;

        for (GameObject object : this.level.getObjects()) {
            object.setGame(this);
        }
    }

    public void start() {
        this.state = GameState.RUNNING;
    }

    public boolean isRunning() {
        return this.state == GameState.RUNNING;
    }

    public void update() {
        for (GameObject object : this.level.getObjects()) {
            if (object instanceof IUpdatable) {
                ((IUpdatable) object).update();
            }
        }
    }

    public Map getMap() {
        return this.level.getMap();
    }

    public Level getLevel() {
        return this.level;
    }

    public void createPlayer(int id) {
        Player localPlayer = new Player(id, "Kobzol");
        localPlayer.setControlledObject(this.level.getObjectByKey(localPlayer.getStandardObjectKey()));

        this.level.addPlayer(localPlayer);
    }

    public boolean collidesWithWalls(ICollidable collidable) {
        for (GameObject object : this.level.getObjects()) {
            if (object instanceof Wall) {
                Wall wall = (Wall) object;

                if (wall.collidesWith(collidable)) {
                    return true;
                }
            }
        }

        return false;
    }
}
