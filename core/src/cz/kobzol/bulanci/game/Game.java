package cz.kobzol.bulanci.game;

import cz.kobzol.bulanci.map.Level;
import cz.kobzol.bulanci.map.Map;
import cz.kobzol.bulanci.model.GameObject;
import cz.kobzol.bulanci.player.Player;

import java.util.List;

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

    public Player getPlayerById(int playerId) {
        return this.level.getPlayerById(playerId);
    }

    public List<GameObject> getObjects() {
        return this.level.getObjects();
    }
}
