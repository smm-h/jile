package jile.math.geometry;

import jile.math.numbers.Real;

public interface Triangle extends Polygon {

    /**
     * @see https://en.wikipedia.org/wiki/Heron%27s_formula
     */
    default public Real getArea() {
        Real s = getPerimeter().divide(2);
        Real a = s;
        for (LineSegment line : getLines()) {
            a = a.multiply(s.subtract(line.getLength()));
        }
        return a.sqrt();
    }

    @Override
    default public Real getPerimeter() {
        Real perimeter = Real.ZERO;
        for (LineSegment line : getLines()) {
            perimeter = perimeter.add(line.getLength());
        }
        return perimeter;
    }
}
