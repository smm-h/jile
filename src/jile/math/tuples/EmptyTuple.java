package jile.math.tuples;

import jile.common.Singleton;

/**
 * An {@link EmptyTuple} is a {@link FiniteTuple} with a length of 0.
 * <p>
 * <b>Alternative names:</b> null tuple / empty sequence / unit
 */
public class EmptyTuple implements FiniteTuple, Singleton, HomogeneousTuple<Object> {

    private EmptyTuple() {
    }

    private static EmptyTuple singleton;

    public static EmptyTuple singleton() {
        if (singleton == null) {
            singleton = new EmptyTuple();
        }
        return singleton;
    }

    @Override
    public Integer getCardinality() {
        return 0;
    }

    @Override
    public Object getElementAt(int index) {
        throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 0-tuple");
    }

    @Override
    public boolean containsUnchecked(Object object) {
        return false;
    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

}