package jile.math.geometry;

import jile.math.exceptions.DimensionMismatchException;
import jile.math.numbers.Real;
import jile.math.tuples.*;

/**
 * A {@link TwoDimensional} {@link LineSegment} is a finite subset of a
 * {@link Line}, defined as a {@link Couple} of 2-dimensional {@link Point}s.
 * 
 * @see HomogeneousTuple
 */
public class LineSegment implements HomogeneousCouple<Point>, Dimensional {

    private final Point first, second;
    private final int dimensions;

    public LineSegment(Point first, Point second) {
        int d = first.getDimensions();
        if (d != second.getDimensions())
            throw new DimensionMismatchException();
        this.first = first;
        this.second = second;
        this.dimensions = d;
    }

    @Override
    public int getDimensions() {
        return dimensions;
    }

    public Real getLength() {
        return EuclideanDistance.singleton().distance(getFirst(), getSecond());
    }

    @Override
    public Point getFirst() {
        return first;
    }

    @Override
    public Point getSecond() {
        return second;
    }
}