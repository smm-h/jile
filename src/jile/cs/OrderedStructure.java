package jile.cs;

/**
 * An {@link OrderedStructure} is a {@link Structure} that has order.
 */
public interface OrderedStructure<T> extends Structure<T> {
    public T get(int index);

    default public T getFirst() {
        return get(0);
    }

    default public T getLast() {
        return get(size() - 1);
    }

    public OrderedStructure<T> slice(int start, int end);

    public OrderedStructure<T> slice(int start);
}
