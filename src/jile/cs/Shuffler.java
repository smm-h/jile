package jile.cs;

import java.util.Random;

/**
 * A {@link Shuffler} is a {@link Measurer} that maps objects to random
 * integers.
 */
public interface Shuffler extends Measurer<Object> {

    public Random getRandom();

    @Override
    default public int measure(Object object) {
        return getRandom().nextInt();
    }
}
