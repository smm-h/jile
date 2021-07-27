package jile.math.series;

import java.util.List;
import java.util.ArrayList;

import jile.math.annotations.BadDesign;
import jile.math.annotations.Inefficient;
import jile.math.annotations.Suboptimal;
import jile.math.numbers.Integer;
import jile.math.numbers.Real;
import jile.math.sequence.InfiniteSequence;
import jile.math.sequence.SpecificSequence;
import jile.common.Random;

/**
 * A {@link Series} is an {@link InfiniteSequence} of {@link T} values.
 * 
 * @see ParasiteSeries
 * @see OffsetSeries
 * @see SumSeries
 * @see ProductSeries
 * @see ArithmeticSeries
 * @see GeometricSeries
 * 
 */
public interface Series<T extends Real> extends InfiniteSequence, SpecificSequence<T> {

    @Override
    default public List<T> excerpt() {
        int n = fairLimit();
        List<T> list = new ArrayList<T>(n);
        for (int i = 0; i < n; i++)
            list.add(i, getElementAt(i));
        return list;
    }

    @BadDesign
    default public List<Integer> excerptWhole() {
        int n = fairLimit();
        List<Integer> list = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            T e = getElementAt(i);
            list.add(i, e.toInteger());
        }
        return list;
    }

    @Inefficient
    @Suboptimal
    @Override
    default public boolean contains(T n) {
        int k = 0;
        T e;
        while (true) {
            e = getElementAt(k++);
            if (e.compareTo(n) >= 0)
                return e.equals(n);
            if (k > fairLimit())
                break;
        }
        return false;
    }

    @Override
    default public int chooseElement() {
        return Random.singleton().nextInt(fairLimit());
    }

    public int fairLimit();

}
