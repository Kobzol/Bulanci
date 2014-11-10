package cz.kobzol.bulanci.game;

import cz.kobzol.bulanci.map.Level;
import cz.kobzol.bulanci.map.Map;

/**
 * Represents the game.
 */
public class Game {
    private Level level;

    public Game(Level level) {
        this.level = level;
    }

    public void update() {

    }

    public Map getMap() {
        return this.level.getMap();
    }

    public Level getLevel() {
        return this.level;
    }
}
