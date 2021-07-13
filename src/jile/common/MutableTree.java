package jile.common;

public interface MutableTree<T> extends TraversibleTree<T> {

    public void addAndGoTo(T data);

    public void add(T data);

    public T remove();

}
