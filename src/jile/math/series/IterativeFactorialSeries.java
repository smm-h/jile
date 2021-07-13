package jile.math.series;

import jile.math.numbers.Integer;

/**
 * The {@link IterativeFactorialSeries} is an implementation of the
 * {@link FactorialSeries} that is calculated using iteration instead of
 * recursion.
 * 
 * @see RecursiveFactorialSeries
 */
public class IterativeFactorialSeries extends FactorialSeries implements IterativeProductSeries<Integer> {

    public static void main(String[] args) {
        System.out.println(new IterativeFactorialSeries().excerptWhole());
    }
}
