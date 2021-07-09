package jile.math.settheory;

import java.util.*;

import jile.common.Random;

/**
 * A {@link StoredSet} is a {@link FiniteSpecificSet} implemented using Java's
 * own {@link HashSet}.
 */
public class StoredSet<T> implements FiniteSpecificSet<T> {

    private final java.util.Set<T> storage;
    private final boolean containsNull;

    public StoredSet(Iterable<T> elements) {
        if (elements != null) {
            boolean foundNull = false;
            storage = new HashSet<T>();
            for (T element : elements) {
                if (element == null)
                    foundNull = true;
                storage.add(element);
            }
            containsNull = foundNull;
        } else {
            storage = null;
            containsNull = false;
        }
    }

    public StoredSet() {
        this(null);
    }

    @Override
    public boolean contains(T object) {
        return storage == null ? false : storage.contains(object);
    }

    @Override
    public T choose() {
        if (storage != null) {
            int chosen = Random.singleton().nextInt(getCardinality());
            int index = 0;
            for (T element : storage) {
                if (index == chosen)
                    return element;
                index++;
            }
        }
        return null;
    }

    @Override
    public Integer getCardinality() {
        if (storage == null)
            return 0;
        else
            return storage.size();
    }

    @Override
    public List<T> excerpt() {
        List<T> list = new LinkedList<T>();
        if (storage != null) {
            // int size = getCardinality();
            // int index = 0;
            for (T element : storage) {
                // if (index < 3 || index >= size - 3)
                list.add(element);
                // index++;
            }
        }
        return list;
    }

    @Override
    public boolean containsNull() {
        return containsNull;
    }

    @Override
    public Iterator<T> iterator() {
        return storage.iterator();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(storage);
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof StoredSet)
            return Objects.equals(storage, ((StoredSet<?>) other).storage);
        else
            return false;
    }
}
