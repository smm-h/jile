package jile.cs;

import java.util.Arrays;
import java.util.Iterator;

import jile.common.Common;
import jile.common.Convert;

/**
 * An {@link ArrayArray} is an {@link Array} implemented using an actual Java
 * array.
 */
public class ArrayArray<T> extends Array<T> {

    private final T[] array;

    @SafeVarargs
    public ArrayArray(T... array) {
        this(array, null, null);
    }

    public ArrayArray(T[] array, Measurer<? super T> measurer) {
        this(array, measurer, null);
    }

    public ArrayArray(T[] array, InplaceSortingAlgorithm<T> sortingAlgorithm) {
        this(array, null, sortingAlgorithm);
    }

    public ArrayArray(T[] array, Measurer<? super T> measurer, InplaceSortingAlgorithm<T> sortingAlgorithm) {
        super(measurer, sortingAlgorithm);
        this.array = array;
    }

    @Override
    public T get(int index) {
        return array[index];
    }

    @Override
    public void set(int index, T value) {
        array[index] = value;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public Iterator<T> iterator() {
        return Common.makeArrayIterator(array);
    }

    @Override
    public ArrayArray<T> slice(int start, int end) {
        return new ArrayArray<T>(Arrays.copyOfRange(array, start, end));
    }

    @Override
    public String toString() {
        return Convert.Array_to_String(array);
    }
}