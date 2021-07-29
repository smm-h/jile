package jile.math.plotting;

import java.awt.Color;
import java.awt.geom.Line2D;

import jile.vis.ParrotGraphics;
import jile.vis.Theatre;
import jile.vis.View;
import jile.vis.Views;
import jile.vis.Visualizable;

public class CartesianPlane implements Visualizable {

    private final ParrotGraphics parrot;
    private final static Color color_axes = new Color(127, 127, 127, 72);
    private final double w, h;
    private double unit_h, unit_v;

    public CartesianPlane(double width, double height, double uh, double uv) {
        w = width / 2;
        h = height / 2;
        unit_h = width / uh;
        unit_v = width / uv;
        parrot = new ParrotGraphics();
        drawAxes();
    }

    private void drawAxes() {
        parrot.new SetColor(color_axes);
        parrot.new DrawShape(new Line2D.Double(-w, 0, w, 0)); // x-axis
        parrot.new DrawShape(new Line2D.Double(0, h, 0, -h)); // y-axis
        double first_x = 0;
        double first_y = 0;
        double last_x = 0;
        double last_y = 0;
        while (first_x - unit_h >= -w)
            first_x -= unit_h;
        while (first_y - unit_v >= -h)
            first_y -= unit_v;
        while (last_x + unit_h <= w)
            last_x += unit_h;
        while (last_y + unit_v <= h)
            last_y += unit_v;
        for (double x = first_x; x <= last_x; x += unit_h) {
            for (double y = first_y; y <= last_y; y += unit_v) {
                parrot.new DrawShape(new Line2D.Double(x - 2, y, x + 2, y));
                parrot.new DrawShape(new Line2D.Double(x, y - 2, x, y + 2));
            }
        }
    }

    public void plot(CartesianEquation f) {
        parrot.new SetColor(Color.GRAY);
        parrot.new SetStroke(Views.makeStroke(3));
        boolean firstTime = true;
        double x, y, px, py;
        px = 0;
        py = 0;
        for (x = -w; x < w; x++) {
            y = f.y(x / unit_h) * unit_v;
            if (firstTime) {
                firstTime = false;
                px = x;
                py = y;
            }
            drawSegment(px, -py, x, -y);
            px = x;
            py = y;
        }
    }

    private void drawSegment(double x1, double y1, double x2, double y2) {
        boolean p1, p2;
        p1 = x1 < w && x1 > -w && y1 < h && y1 > -h;
        p2 = x2 < w && x2 > -w && y2 < h && y2 > -h;
        if (p1 || p2)
            parrot.new DrawShape(new Line2D.Double(x1, y1, x2, y2));
        // if (p1 && p2) {
        // parrot.new DrawShape(new Line2D.Double(x1, y1, x2, y2));
        // } else if (p1) {
        // // clip p2 and redraw
        // parrot.new DrawShape(new Line2D.Double(x1, y1, x2, y2));
        // } else if (p2) {
        // // clip p1 and redraw
        // parrot.new DrawShape(new Line2D.Double(x1, y1, x2, y2));
        // }
    }

    @Override
    public View visualize() {
        Theatre theatre = parrot.visualize();
        theatre.keepLooping.set(false);
        theatre.pace.set(1L);
        return theatre;
    }
}
