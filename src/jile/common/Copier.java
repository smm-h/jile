package jile.common;

public interface Copier<T> {
    public T copy(T source);

    public class NullCopier<T> implements Copier<T> {

        @Override
        public T copy(T source) {
            return source;
        }
    }
}