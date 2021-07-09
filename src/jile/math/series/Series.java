package jile.math.series;

import java.util.List;
import java.util.ArrayList;

import jile.math.annotations.BadDesign;
import jile.math.annotations.Inefficient;
import jile.math.sequence.InfiniteSequence;
import jile.math.sequence.SpecificSequence;
import jile.common.Random;

/**
 * A {@link Series} is an {@link InfiniteSequence} of {@link Double} values.
 */
public interface Series extends InfiniteSequence, SpecificSequence<Double> {

    @Override
    default public List<Double> excerpt() {
        int n = fairLimit();
        List<Double> list = new ArrayList<Double>(n);
        for (int i = 0; i < n; i++)
            list.add(i, getElementAt(i));
        return list;
    }

    @BadDesign
    default public List<Long> excerptWhole() {
        int n = fairLimit();
        List<Long> list = new ArrayList<Long>(n);
        for (int i = 0; i < n; i++) {
            double e = getElementAt(i);
            list.add(i, (long) e);
        }
        return list;
    }

    @Inefficient
    @Override
    default public boolean contains(Double n) {
        int k = 0;
        double e;
        while (true) {
            e = getElementAt(k++);
            if (e >= n)
                return e == n;
            if (k > fairLimit())
                break;
        }
        return false;
    }

    /**
     * Series are not nullibe.
     */
    @Override
    default public boolean containsNull() {
        return false;
    }

    @Override
    default public int chooseElement() {
        return Random.singleton().nextInt(fairLimit());
    }

    public int fairLimit();

}
