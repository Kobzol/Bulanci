package cz.kobzol.bulanci.map;

import cz.kobzol.bulanci.model.GameObject;
import cz.kobzol.bulanci.model.IGameObject;
import cz.kobzol.bulanci.model.ObjectManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents game level with map and game objects.
 */
public class Level {
    private Map map;
    private final ObjectManager<GameObject> objectManager;

    public Level() {
        this.objectManager = new ObjectManager<GameObject>();
    }

    public Map getMap() {
        return this.map;
    }
    public void setMap(Map map) {
        this.map = map;
    }

    public List<GameObject> getObjects() {
        return this.objectManager.getObjects();
    }

    public GameObject getObjectById(int id) {
        return this.objectManager.getObjectById(id);
    }
    public GameObject getObjectByKey(String key) {
        return this.objectManager.getObjectByKey(key);
    }

    public void addObject(GameObject object) {
        try {
            this.objectManager.registerObject(object);
        } catch (ObjectManager.ObjectAlreadyRegisteredException e) {
            e.printStackTrace();
        } catch (ObjectManager.AnotherObjectHasSameKeyException e) {
            e.printStackTrace();
        } catch (ObjectManager.AnotherObjectHasSameIdException e) {
            e.printStackTrace();
        }
    }
}
