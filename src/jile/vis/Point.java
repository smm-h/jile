package jile.vis;

import java.awt.geom.Point2D;

public class Point extends Point2D.Double {

    public Point(double x, double y) {
        super(x, y);
    }

    public static Point fromPoint(Point point) {
        return new Point(point.getX(), point.getY());
    }

    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }

    public Point translate(Point relative) {
        return new Point(x + relative.x, y + relative.y);
    }
}
