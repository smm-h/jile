package jile.math.sequence;

import jile.common.Random;

/**
 * A {@link FiniteSequence} is a {@link Sequence} whose length is finite, and
 * thus can be converted to an array.
 */
public interface FiniteSequence extends Sequence {

    @Override
    default boolean isFinite() {
        return true;
    }

    default public Object[] toArray() {
        int length = getCardinality();
        Object[] array = new Object[length];
        for (int i = 0; i < length; i++)
            array[i] = getElementAt(i);
        return array;
    }

    @Override
    default public int chooseElement() {
        return Random.singleton().nextInt(getCardinality());
    }

}
