package jile.math.sequence;

import java.util.Iterator;
import java.util.Objects;

/**
 * A {@link FiniteSpecificSequence} is a sequence of finitely-many elements
 * ({@link FiniteSequence}) that are all objects of a specific type
 * ({@link SpecificSequence}).
 * 
 * @implNote To implement this interface, you only need to override
 *           {@link #getCardinality()} and {@link #getElementAt(int index)}.
 */
public interface FiniteSpecificSequence<T> extends FiniteSequence, SpecificSequence<T>, Iterable<T> {

    @SuppressWarnings("unchecked")
    default public T[] toGenericArray() {
        int length = getCardinality();
        T[] array = (T[]) new Object[length];
        for (int i = 0; i < length; i++)
            array[i] = getElementAt(i);
        return array;
    }

    @Override
    default boolean contains(T object) {
        int length = getCardinality();
        for (int i = 0; i < length; i++)
            if (Objects.equals(getElementAt(i), object))
                return true;
        return false;
    }

    @Override
    default Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < getCardinality();
            }

            @Override
            public T next() {
                return getElementAt(index++);
            }
        };
    }
}
