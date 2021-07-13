package jile.cs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An {@link ListArray} is an {@link Array} implemented using a Java
 * {@link List}.
 */
public class ListArray<T> extends Array<T> {

    private final List<T> list;

    public ListArray(List<T> list) {
        this(list, null, null);
    }

    public ListArray(List<T> list, Measurer<? super T> measurer) {
        this(list, measurer, null);
    }

    public ListArray(List<T> list, InplaceSortingAlgorithm<T> sortingAlgorithm) {
        this(list, null, sortingAlgorithm);
    }

    public ListArray(List<T> list, Measurer<? super T> measurer, InplaceSortingAlgorithm<T> sortingAlgorithm) {
        super(measurer, sortingAlgorithm);
        this.list = list;
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public void set(int index, T value) {
        list.set(index, value);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public ListArray<T> slice(int start, int end) {
        List<T> sliced = new ArrayList<T>(end - start);
        for (int index = start; index < end; index++)
            sliced.add(list.get(index));
        return new ListArray<T>(sliced, getDefaultMeasurer(), getDefaultSortingAlgorithm());
    }
}