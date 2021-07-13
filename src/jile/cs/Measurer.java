package jile.cs;

/**
 * A {@link Measurer} is an object responsible for somehow "measuring" a certain
 * aspect of any given object of an specific type, which means mapping them to
 * integers, ideally uniquely and typically deterministically.
 * 
 * @implNote use it with {@code ? super T}.
 */
public interface Measurer<T> {
    public int measure(T object);
}