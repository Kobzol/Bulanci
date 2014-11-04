package cz.kobzol.bulanci.map;

import cz.kobzol.bulanci.model.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents game level with map and game objects.
 */
public class Level {
    private Map map;
    private final List<GameObject> objects;

    public Level() {
        this.objects = new ArrayList<GameObject>();
    }

    public Map getMap() {
        return this.map;
    }
    public void setMap(Map map) {
        this.map = map;
    }

    public List<GameObject> getObjects() {
        return this.objects;
    }

    public GameObject getObjectById(int id) {
        for (GameObject object : this.objects) {
            if (object.getId() == id) {
                return object;
            }
        }

        return null;
    }
    public GameObject getObjectByKey(String key) {
        for (GameObject object : this.objects) {
            if (object.getKey().equals(key)) {
                return object;
            }
        }

        return null;
    }

    public void addObject(GameObject object) {
        this.objects.add(object);
    }
}
