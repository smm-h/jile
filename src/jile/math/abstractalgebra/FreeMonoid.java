package jile.math.abstractalgebra;

import jile.math.annotations.Inefficient;
import jile.math.operations.*;
import jile.math.settheory.*;
import jile.math.sequence.*;
import jile.common.Random;

/**
 * A {@link FreeMonoid} on set S is denoted as S* and is a {@link Monoid} whose
 * elements are {@link FiniteSequence}s of elements of S, with
 * {@link Concatenation} as the monoid operation, and the zero-length sequence
 * (ε/λ) as the identity element.
 */

public interface FreeMonoid<T> extends Monoid<FiniteSpecificSequence<T>> {

    public SpecificSet<T> getFreeSet();

    @Override
    @Inefficient
    default public FiniteSpecificSequence<T> getIdentity() {
        return new ArraySequence<T>();
    }

    @Override
    @Inefficient
    default public Concatenation<FiniteSpecificSequence<T>> getOperation() {
        return new Concatenation<FiniteSpecificSequence<T>>() {

            @Override
            @SuppressWarnings("unchecked")
            public FiniteSpecificSequence<T> operate(FiniteSpecificSequence<T> a, FiniteSpecificSequence<T> b) {

                final int n = a.getCardinality();
                final int m = b.getCardinality();

                T[] concatenated = (T[]) new Object[n + m];

                for (int i = 0; i < n; i++)
                    concatenated[i] = a.getElementAt(i);

                for (int i = 0; i < m; i++)
                    concatenated[n + i] = b.getElementAt(i);

                return new ArraySequence<T>(concatenated);
            }
        };

    }

    @Override
    @Inefficient
    default public SpecificSet<FiniteSpecificSequence<T>> getSet() {
        return new InfiniteUniversalSet<FiniteSpecificSequence<T>>() {
            @Override
            @SuppressWarnings("unchecked")
            public FiniteSpecificSequence<T> choose() {
                // ((FiniteSpecificSequence<T>) null).
                int length = Random.singleton().nextInt(10);
                T[] sequence = (T[]) new Object[length];
                SpecificSet<T> freeSet = getFreeSet();
                for (int i = 0; i < length; i++)
                    sequence[i] = freeSet.choose();
                return new ArraySequence<T>(sequence);
            }
        };
    }
}
