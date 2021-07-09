package jile.math.series;

/**
 * The {@link IterativeFactorialSeries} is an implementation of the
 * {@link FactorialSeries} that is calculated using iteration instead of
 * recursion.
 * 
 * @see RecursiveFactorialSeries
 */
public class IterativeFactorialSeries extends FactorialSeries implements IterativeProductSeries {

    public static void main(String[] args) {
        System.out.println(new IterativeFactorialSeries().excerptWhole());
    }
}
