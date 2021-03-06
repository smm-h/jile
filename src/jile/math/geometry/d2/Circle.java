package jile.math.geometry.d2;

import jile.math.geometry.Drawing;
import jile.math.numbers.Real;

public class TwoDimensionalCircle extends Drawing {

    private final TwoDimensionalPoint origin;
    private final Real radius;

    public Circle(Real x, Real y, Real radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

}
