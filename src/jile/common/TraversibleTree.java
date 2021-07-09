package jile.common;

public interface TraversibleTree<T> extends Tree<T> {

    public <T2> LinkedTree<T2> convert(Convertor<T, T2> convertor);

    public <T2> Tree<T2> cast();

    public void goTo(T data);

    public T getPointer();

    public void goToLastAdded();

    public void goToChild(int childIndex) throws IndexOutOfBoundsException;

    public void goToRoot();

    public void goBack();

}