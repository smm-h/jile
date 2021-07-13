package jile.math.series;

import jile.math.numbers.Real;

/**
 * Converges toward pi. A very slow but simple way to approximate pi.
 * 
 * @see https://en.wikipedia.org/wiki/Leibniz_formula_for_%CF%80
 */
public class GregoryLeibnizSeries implements RecursiveSumSeries<Real> {

    private final Real n4 = Real.fromContents(-4);
    private final Real p4 = Real.fromContents(+4);

    private final Series<Real> host = new Series<Real>() {

        @Override
        public int fairLimit() {
            return 100;
        }

        @Override
        public Real getElementAt(int k) {
            return (k % 2 == 0 ? n4 : p4).divide(k * 2 + 1);
        }
    };

    @Override
    public Series<Real> getHostSeries() {
        return host;
    }
}
