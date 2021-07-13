package jile.math.settheory;

/**
 * An implicitly "specific" {@link UniversalSet} contains every possible object
 * of a specific type and is thus a {@link SpecificSet} of that type (which or
 * may not be infinite, see {@link FiniteUniversalSet}), that returns true when
 * asked if it contains an object of that type.
 * <p>
 * A "non-specific" UniversalSet does not exists because it would contain
 * itself, and therefore cause Russel's paradox and violate the "axiom of
 * regularity": https://en.wikipedia.org/wiki/axiom_of_regularity
 */
public interface UniversalSet<T> extends SpecificSet<T> {
    @Override
    default public boolean contains(T object) {
        return object == this ? false : true;
    }
}
