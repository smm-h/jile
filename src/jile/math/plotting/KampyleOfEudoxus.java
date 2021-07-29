package jile.math.plotting;

import jile.common.Common;
import jile.vis.Viewer;

/**
 * @see https://en.wikipedia.org/wiki/Kampyle_of_Eudoxus
 */
public class KampyleOfEudoxus implements CartesianEquation {

    private final double a;

    public KampyleOfEudoxus(double a) {
        this.a = a;
    }

    @Override
    public double y(double x) {
        // x4=a2(x2+y2)
        // x2((x/a)2-1)=y2
        // y = +- x sqrt((x/a)2-1)
        return x * Common.sqrt(Common.sqr(x / a) - 1);
    }

    public static void main(String[] args) {
        CartesianPlane p = new CartesianPlane(300, 600, 5, 4);
        CartesianEquation k = new KampyleOfEudoxus(1);
        p.plot(k);
        p.plot(k.negate());
        Viewer.singleton().show(p.visualize());
    }
}
