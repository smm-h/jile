package jile.math.settheory;

/**
 * A {@link NonNullibleSet} is a {@link Set} that does not contain the special
 * object "null".
 */
public interface NonNullibleSet extends Set {

    @Override
    default public boolean containsNull() {
        return false;
    }
}
