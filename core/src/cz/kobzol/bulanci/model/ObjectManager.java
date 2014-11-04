package cz.kobzol.bulanci.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectManager
{

    protected List<IGameObject> objects;

    protected HashMap<Integer, IGameObject> objectsId;

    protected HashMap<String, IGameObject> objectsKey;

    protected List<Listener> listeners;

    public ObjectManager() {
        this.objects = new ArrayList<IGameObject>();
        this.objectsId = new HashMap<Integer, IGameObject>();
        this.objectsKey = new HashMap<String, IGameObject>();
        this.listeners = new ArrayList<Listener>();
    }

    public void registerListener(Listener listener) {
        this.listeners.add(listener);
    }

    public ArrayList<IGameObject> getObjects()
    {
        return new ArrayList<IGameObject>(this.objects);
    }

    public IGameObject getObjectById(int id) {
        return objectsId.get(id);
    }

    public IGameObject getObjectByKey(String key) {
        return objectsKey.get(key);
    }

    public void registerObject(IGameObject object) throws ObjectRegistrationException {
        if (objectsId.containsKey(object.getId())) {
            if (objectsId.get(object.getId()) == object) {
                throw new ObjectAlreadyRegisteredException(object);
            } else {
                throw new AnotherObjectHasSameIdException(objectsId.get(object.getId()), object);
            }
        }
        if (objectsKey.containsKey(object.getKey())) {
            throw new AnotherObjectHasSameKeyException(objectsKey.get(object.getKey()), object);
        }

        this.objects.add(object);
        this.objectsId.put(object.getId(), object);
        this.objectsKey.put(object.getKey(), object);
    }

    public void removeObject(IGameObject object) {
        for (Listener listener : listeners) {
            listener.onRemove(this, object);
        }
        if (object instanceof RemoveEventObject) {
            ((RemoveEventObject) object).onRemove(this);
        }
        this.objects.remove(object);
        this.objectsId.remove(object.getId());
        this.objectsKey.remove(object.getKey());
    }


    /***** Listener *****/

    public interface Listener {

        /**
         * When object is unregistered from Object manager, this is be called before real remove
         * @param objectManager from removed
         * @param object which is removed
         */
        public void onRemove(ObjectManager objectManager, IGameObject object);

    }


    public interface RemoveEventObject {

        /**
         * When object is unregistered from Object manager, this is be called before real remove
         * @param objectManager from removed
         */
        public void onRemove(ObjectManager objectManager);

    }


    /***** Exceptions *****/

    private class ObjectRegistrationException extends Exception {
        private IGameObject registered;
        private IGameObject another;

        public ObjectRegistrationException(IGameObject registered, IGameObject another, String message) {
            super(message);
            this.registered = registered;
            this.another = another;
        }

        public IGameObject getRegistered() {
            return registered;
        }

        public IGameObject getAnother() {
            return another;
        }
    }

    public class ObjectAlreadyRegisteredException extends ObjectRegistrationException {
        public ObjectAlreadyRegisteredException(IGameObject registered) {
            super(registered, registered, "Object is already registered in object manager");
        }
    }
    public class AnotherObjectHasSameKeyException extends ObjectRegistrationException {
        public AnotherObjectHasSameKeyException(IGameObject registered, IGameObject another) {
            super(registered, another, "Game object with same key and not same instance want to register");
        }
    }
    public class AnotherObjectHasSameIdException extends ObjectRegistrationException {
        public AnotherObjectHasSameIdException(IGameObject registered, IGameObject another) {
            super(registered, another, "Game object with same id and not same instance want to register");
        }
    }
}
