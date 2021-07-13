package jile.math.settheory;

public interface OrderedSpecificSet<T> extends OrderedSet, SpecificSet<T> {

    public Integer findOrdinalOfElement(T element);

    @Override
    public T getElementAt(int ordinal);
}
