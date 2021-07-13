package jile.cs;

/**
 * A {@link Structure} is the fashion in which a number of objects are put
 * together in the memory, which affects how they are accessed, and possibly
 * mutated.
 */
public interface Structure<T> extends Iterable<T> {

    public int size();

}
