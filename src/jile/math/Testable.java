package jile.math;

/**
 * The base interface for almost everything in this package.
 */
public interface Testable {
    default public boolean test() {
        return true;
    };
}