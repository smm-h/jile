package jile.common.q;

// import static jile.common.Quality.*; // you are going to need this

/**
 * @see https://docs.oracle.com/javase/tutorial/java/generics/wildcardGuidelines.html
 */
public interface Quality<T> {
    public boolean holdsFor(T object);

    public static <T> boolean is(T object, Quality<? super T> q) {
        return q.holdsFor(object);
    }

    public static <T> boolean is_not(T object, Quality<? super T> q) {
        return !q.holdsFor(object);
    }

    public static <T> Quality<? super T> not(Quality<? super T> q) {
        return new Quality<T>() {
            @Override
            public boolean holdsFor(T object) {
                return !q.holdsFor(object);
            }
        };
    }

    public static <T> Quality<? super T> and(Quality<? super T> q, Quality<? super T> r) {
        return new Quality<T>() {
            @Override
            public boolean holdsFor(T object) {
                return q.holdsFor(object) && r.holdsFor(object);
            }
        };
    }

    public static <T> Quality<? super T> or(Quality<? super T> q, Quality<? super T> r) {
        return new Quality<T>() {
            @Override
            public boolean holdsFor(T object) {
                return q.holdsFor(object) || r.holdsFor(object);
            }
        };
    }
}