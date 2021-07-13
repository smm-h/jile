package jile.math.sequence;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import jile.common.Common;
import jile.common.Convert;

public class ArraySequence<T> implements FiniteSpecificSequence<T> {

    private final T[] storage;

    public ArraySequence(T[] elements) {
        storage = elements.clone();
    }

    public ArraySequence(List<T> elements) {
        storage = Convert.List_to_Array(elements);
    }

    public ArraySequence() {
        storage = null;
    }

    @Override
    public T getElementAt(int index) {
        return storage[index];
    }

    @Override
    public Integer getCardinality() {
        if (storage == null)
            return 0;
        else
            return storage.length;
    }

    @Override
    public Iterator<T> iterator() {
        return Common.makeArrayIterator(storage);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(storage);
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof ArraySequence)
            return Objects.equals(storage, ((ArraySequence<?>) other).storage);
        else
            return false;
    }

    public java.lang.String toString() {
        StringBuilder builder = new StringBuilder();
        for (T element : this) {
            builder.append(element.toString());
        }
        return builder.toString();
    }

    public static ArraySequence<Object> fromArray(Object... array) {
        return new ArraySequence<Object>(array);
    }

    public static <T> ArraySequence<T> fromGenericArray(T[] array) {
        return new ArraySequence<T>(array);
    }

}
