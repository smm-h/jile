package jile.math.geometry;

import jile.math.sequence.FiniteSpecificSequence;

/**
 * A {@link PolygonalChain} is a connected sequence of {@link Point}s that form
 * a series of consecutive {@link LineSegment}s.
 */
public interface PolygonalChain extends FiniteSpecificSequence<Point>, GeometricalObject {
    default public FiniteSpecificSequence<LineSegment> getLines() {
        return new FiniteSpecificSequence<LineSegment>() {
            @Override
            public Integer getCardinality() {
                return PolygonalChain.this.getCardinality() - 1;
            }

            @Override
            public LineSegment getElementAt(int index) {
                return new LineSegment(PolygonalChain.this.getElementAt(index),
                        PolygonalChain.this.getElementAt(index + 1));
            }
        };
    }
}
