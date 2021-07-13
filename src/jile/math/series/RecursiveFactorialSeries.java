package jile.math.series;

import jile.math.numbers.Integer;

/**
 * The {@link RecursiveFactorialSeries} is an implementation of the
 * {@link FactorialSeries} that is calculated using recursion.
 * 
 * @see IterativeFactorialSeries
 */
public class RecursiveFactorialSeries extends FactorialSeries implements RecursiveProductSeries<Integer> {

    public static void main(String[] args) {
        System.out.println(new RecursiveFactorialSeries().excerptWhole());
    }
}
