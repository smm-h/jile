package jile.math.geometry;

import jile.math.numbers.Real;

public interface Triangle extends Polygon {

    default public LineSegment getEdgeAB() {
        return new LineSegment(a, b);
    }

    default public LineSegment getEdgeBC() {
        return new LineSegment(b, c);
    }

    default public LineSegment getEdgeCA() {
        return new LineSegment(c, a);
    }

    /**
     * @see https://en.wikipedia.org/wiki/Heron%27s_formula
     */
    default public Real getArea() {
        Real s = Real.divide(getPerimeter(), Real.TWO);
        Real a = s;
        a = Real.multiply(a, Real.subtract(s, getEdgeAB().getLength()));
        a = Real.multiply(a, Real.subtract(s, getEdgeBC().getLength()));
        a = Real.multiply(a, Real.subtract(s, getEdgeCA().getLength()));
        return Real.sqrt(a);
    }

    @Override
    default public Real getPerimeter() {
        Real perimeter = Real.ZERO;
        perimeter = Real.add(perimeter, getEdgeAB().getLength());
        perimeter = Real.add(perimeter, getEdgeBC().getLength());
        perimeter = Real.add(perimeter, getEdgeCA().getLength());
        return perimeter;
    }
}
