package jile.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HashMapCache<Wrapper, Contents> implements Cache<Wrapper, Contents> {

    private final Map<Integer, Contents> mapOfContents = new HashMap<Integer, Contents>();
    private final Map<Integer, Wrapper> mapOfWrappers = new HashMap<Integer, Wrapper>();

    @Override
    public Wrapper get(Contents contents) {
        int key = contents.hashCode();
        if (mapOfContents.containsKey(key)) {
            Contents cachedContents = mapOfContents.get(key);
            if (Objects.equals(cachedContents, contents)) {
                return mapOfWrappers.get(key);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void add(Contents contents, Wrapper wrapper) throws CacheCollisionException {
        int key = contents.hashCode();
        if (mapOfContents.containsKey(key)) {
            Contents cachedContents = mapOfContents.get(key);
            if (!Objects.equals(cachedContents, contents)) {
                throw new CacheCollisionException();
            }
        } else {
            mapOfContents.put(key, contents);
            mapOfWrappers.put(key, wrapper);
        }
    }

}
