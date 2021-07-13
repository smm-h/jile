package jile.common.q;

// import static jile.common.q.Quality.*;

public interface ClassQualities {

    public static final Quality<Class<?>> _interface = new Quality<Class<?>>() {
        @Override
        public boolean holdsFor(Class<?> c) {
            return c.isInterface();
        }
    };

}
