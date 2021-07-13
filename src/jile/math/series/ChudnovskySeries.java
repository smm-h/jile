package jile.math.series;

import jile.math.numbers.ConvergentPi;
import jile.math.numbers.Pi;
import jile.math.numbers.Real;
import jile.common.Common;

/**
 * The {@link ChudnovskySeries} is a {@link SumSeries} that converges toward
 * 1/{@link Pi}. It is the fastest way known to approximate pi.
 * 
 * @see https://en.wikipedia.org/wiki/Chudnovsky_algorithm
 * @see ConvergentPi
 */
public class ChudnovskySeries implements RecursiveSumSeries<Real> {
    private final Series<Real> host = new Series<Real>() {

        @Override
        public int fairLimit() {
            return 10;
        }

        @Override
        public Real getElementAt(int k) {
            return Real.fromContents(12.0 * (Math.pow(-1, k) * factorial(6 * k) * (545140134 * k + 13591409))
                    / (factorial(3 * k) * Math.pow(factorial(k), 3) * Math.pow(640320, 3 * k + 3.0 / 2.0)));
        }
    };

    @Override
    public Series<Real> getHostSeries() {
        return host;
    }

    private double factorial(double n) {
        // return RecursiveFactorialSeries().getElementAt((int) n); TODO
        return Common.factorial(n);
    }

}
