package jile.lingu;

import java.awt.*;
import java.awt.geom.Line2D;
// import java.awt.image.BufferedImage;

import jile.common.Identifiable;
import jile.vis.*;
import jile.vis.Point;

// @SuppressWarnings("serial")
public class TurtleGraphics implements Decodeable, Identifiable {

    private final ParrotGraphics parrot;
    // private double minX, minY, maxX, maxY;

    public TurtleGraphics() {
        this((Code) null);
    }

    private final Code sourceCode;

    @Override
    public Code getSourceCode() {
        return sourceCode;
    }

    public TurtleGraphics(ParrotGraphics parrot) {
        this(parrot.getSourceCode());
    }

    // public Image getImage() {
    // int w = (int) Math.ceil(maxX);
    // int h = (int) Math.ceil(maxY);
    // var image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

    // var g = image.createGraphics();
    // Views.applyCoolness(g);
    // g.setStroke(new BasicStroke(2));
    // g.setColor(Color.DARK_GRAY);
    // parrot.beDrawnOn(g);

    // return image;
    // }

    public TurtleGraphics(Code sourceCode) {
        this.sourceCode = sourceCode;
        parrot = new ParrotGraphics();
        parrot.new SetStroke(new BasicStroke(2));
        parrot.new SetColor(Color.DARK_GRAY);
    }

    public ParrotGraphics getParrot() {
        return parrot;
    }

    private static final Fabric fabric = new Fabric(1);

    public class Turtle {

        private final Angle angle;
        private Point point;
        private float scale;

        private final Color color = fabric.toColor(new Dye((float) Math.random()));

        public Turtle() {
            this.angle = Angle.fromDegrees(0);
            this.point = new Point(320, 240);
            this.scale = 1;
        }

        public Turtle(Turtle cause) {
            this.angle = Angle.fromAngle(cause.angle);
            this.point = Point.fromPoint(cause.point);
            this.scale = cause.scale;
        }

        public void go(double distance, boolean draw) {
            var newPoint = point.translate(angle.makePoint(distance * scale));
            if (draw) {
                double x1 = point.getX();
                double y1 = point.getY();
                double x2 = newPoint.getX();
                double y2 = newPoint.getY();
                // minX = Math.min(minX, Math.min(x1, x2));
                // minY = Math.min(minY, Math.min(y1, y2));
                // maxX = Math.max(maxX, Math.max(x1, x2));
                // maxY = Math.max(maxY, Math.max(y1, y2));
                parrot.new SetColor(color);
                parrot.new DrawShape(new Line2D.Double(x1, y1, x2, y2));
            }
            point = newPoint;
        }

        public void turn(double degrees) {
            angle.addDegrees(degrees);
        }

        public void setScale(float factor) {
            scale *= factor;
        }

        public float getScale() {
            return scale;
        }
    }

    @Override
    public String getIdentity() {
        return "Turtle Graphics" + (sourceCode == null ? "" : " of " + sourceCode.getIdentity());
    }
}