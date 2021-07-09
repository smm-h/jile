package jile.math.settheory;

/**
 * A {@link NullibleSet} is a {@link Set} that does not contain the special
 * object "null".
 */
public interface NullibleSet extends Set {

    @Override
    default public boolean containsNull() {
        return true;
    }
}
