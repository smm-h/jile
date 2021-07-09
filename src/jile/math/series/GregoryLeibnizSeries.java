package jile.math.series;

/**
 * Converges toward pi. A very slow but simple way to approximate pi.
 * 
 * @see https://en.wikipedia.org/wiki/Leibniz_formula_for_%CF%80
 */
public class GregoryLeibnizSeries implements RecursiveSumSeries {

    private final Series host = new Series() {

        @Override
        public int fairLimit() {
            return 100;
        }

        @Override
        public Double getElementAt(int k) {
            return (k % 2 == 0 ? +4.0 : -4.0) / (k * 2 + 1);
        }
    };

    @Override
    public Series getHostSeries() {
        return host;
    }
}
