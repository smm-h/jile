package jile.math.series;

/**
 * The {@link RecursiveFactorialSeries} is an implementation of the
 * {@link FactorialSeries} that is calculated using recursion.
 * 
 * @see IterativeFactorialSeries
 */
public class RecursiveFactorialSeries extends FactorialSeries implements RecursiveProductSeries {

    public static void main(String[] args) {
        System.out.println(new RecursiveFactorialSeries().excerptWhole());
    }
}
