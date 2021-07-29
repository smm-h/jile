package jile.math.plotting;

import jile.common.Common;
import jile.vis.Viewer;

public class PlotTest {

    public static void main(String[] args) {
        CartesianPlane p = new CartesianPlane(512, 512, 8, 8);
        // p.plot(x -> x);
        p.plot(x -> Common.sqr(x));
        // p.plot(x -> Common.sqrt(Common.sqr(x) * 2));
        // p.plot(x -> x * Common.sqrt(Common.sqr(x / 1) - 1));
        Viewer.singleton().show(p.visualize());
    }
}
