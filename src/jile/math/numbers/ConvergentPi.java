package jile.math.numbers;

import jile.math.series.ChudnovskySeries;
// import jile.math.series.GregoryLeibnizSeries;

/**
 * A {@link ConvergentPi} is a {@link Convergent} that uses the
 * {@link ChudnovskySeries} to converge to {@link Pi}.
 */
public class ConvergentPi extends BaseIrrationalConvergent implements Pi {

    private ConvergentPi() {
        super(20);
    }

    @Override
    public double converge(int depth) {
        return 1 / new ChudnovskySeries().getElementAt(depth);
        // return new GregoryLeibnizSeries().getElementAt(depth);
    }

    public static void main(String[] args) {
        System.out.println(new ConvergentPi().approximate());
    }

}
