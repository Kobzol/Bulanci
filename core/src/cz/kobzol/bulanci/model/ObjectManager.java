package cz.kobzol.bulanci.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectManager<T extends IGameObject>
{

    protected List<T> objects;

    protected HashMap<Integer, T> objectsId;

    protected HashMap<String, T> objectsKey;

    protected List<Listener> listeners;

    public ObjectManager() {
        this.objects = new ArrayList<T>();
        this.objectsId = new HashMap<Integer, T>();
        this.objectsKey = new HashMap<String, T>();
        this.listeners = new ArrayList<Listener>();
    }

    public void registerListener(Listener listener) {
        this.listeners.add(listener);
    }

    public ArrayList<T> getObjects()
    {
        return new ArrayList<T>(this.objects);
    }

    public T getObjectById(int id) {
        return objectsId.get(id);
    }

    /**
     * Find object by his key (key is no registered)
     * Implementation: If some object is renamed, ObjectManager try find them *only* based on new key (like foreach object).
     * @param key
     * @return T|null
     */
    public T getObjectByKey(String key) {
        if (objectsKey.containsKey(key)) {
            T object = objectsKey.get(key);
            if (object.getKey().equals(key)) {
                return object;
            } else {
                objectsKey.remove(key);
                objectsKey.put(object.getKey(), object);
            }
        }
        for (T object : objects) {
            if (object.getKey().equals(key)) {
                objectsKey.put(object.getKey(), object);
                return object;
            }
        }
        return null;
    }

    /**
     * Register new object (first) and call event (second)
     * @param object
     * @throws ObjectRegistrationException
     */
    public void registerObject(T object) throws
            ObjectAlreadyRegisteredException,
            AnotherObjectHasSameIdException,
            AnotherObjectHasSameKeyException
    {
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

        if (object instanceof RegisteredEventObject) {
            ((RegisteredEventObject) object).onRegistered(this);
        }
        for (Listener listener : listeners) {
            listener.onRegistered(this, object);
        }

    }


    /**
     * Unregister object (second), and call event (first)
     * @param object
     */
    public void removeObject(T object) {
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
         * When object is registered from Object manager, this is be called after is registered
         * @param objectManager to added
         * @param object which is added
         */
        public void onRegistered(ObjectManager objectManager, IGameObject object);

        /**
         * When object is unregistered from Object manager, this is be called before real remove
         * @param objectManager from removed
         * @param object which is removed
         */
        public void onRemove(ObjectManager objectManager, IGameObject object);

    }

    public interface RegisteredEventObject {

        /**
         * When object is registered from Object manager, this is be called after is registered
         * @param objectManager to added
         */
        public void onRegistered(ObjectManager objectManager);

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
