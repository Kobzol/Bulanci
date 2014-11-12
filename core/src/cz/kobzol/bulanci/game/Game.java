package cz.kobzol.bulanci.game;

import cz.kobzol.bulanci.map.Level;
import cz.kobzol.bulanci.map.Map;
import cz.kobzol.bulanci.model.GameObject;
import cz.kobzol.bulanci.model.IUpdatable;

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
}
