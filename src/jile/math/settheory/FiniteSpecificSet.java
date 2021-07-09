package jile.math.settheory;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FiniteSpecificSet} is a finite set ({@link FiniteSet}) whose
 * elements are all objects of a specific type ({@link SpecificSet}).
 */
public interface FiniteSpecificSet<T> extends FiniteSet, SpecificSet<T>, Iterable<T> {

    @Override
    default public List<T> excerpt() {
        int n = getCardinality();
        List<T> list = new ArrayList<T>(n);
        int i = 0;
        for (T element : this)
            list.add(i++, element);
        return list;
    }

}
