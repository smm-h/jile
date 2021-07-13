package jile.cs;

import java.util.Random;

/**
 * A {@link Filler} generates random objects generating many instances of class
 * {@code T} and filling a {@link MyArray} object with them.
 */
public interface Filler<T> {
    public T fill(Random random, int magnitude);
}