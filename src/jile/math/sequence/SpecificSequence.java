package jile.math.sequence;

import jile.math.settheory.*;

/**
 * An {@link SpecificSequence} is a {@link Sequence} whose values are of a
 * certain type; in this sense it is also an ordered ({@link OrderedSet})
 * specific ({@link SpecificSet}) set.
 * 
 * @see FiniteSpecificSequence
 */
public interface SpecificSequence<T> extends Sequence, SpecificSet<T> {
    @Override
    public T getElementAt(int index);

    @Override
    public default T choose() {
        return getElementAt(chooseElement());
    }
}
