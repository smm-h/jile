package jile.math.settheory.eulerdiagram;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

// import jile.common.Service;
import jile.math.settheory.FiniteUniversalSet;
import jile.vis.View;

public class EulerDiagram<T> extends View {

    private final double w, h;

    private List<Representative> representatives;

    private static final double diameter = 16;

    private class Representative extends Element<T> {

        Ellipse2D shape;
        T model;

        Representative(T model) {
            this.model = model;
            double x0, y0;
            x0 = Math.random() * (w - diameter) + diameter / 2;
            y0 = Math.random() * (h - diameter) + diameter / 2;
            shape = new Ellipse2D.Double(x0, y0, diameter, diameter);
        }

        @Override
        public T getModel() {
            return model;
        }

        @Override
        public Shape getShape() {
            return shape;
        }

        @Override
        public String toString() {
            return model.toString();
        }
    }

    public EulerDiagram(FiniteUniversalSet<T> u) {
        this(u, 720, 360);
    }

    public EulerDiagram(FiniteUniversalSet<T> u, int w, int h) {
        super(Mode.INTERACTIVE);
        this.w = w;
        this.h = h;
        setSize(w, h);
        int n = u.getCardinality();
        representatives = new ArrayList<Representative>(n);
        for (T model : u) {
            representatives.add(new Representative(model));
        }
        // Service service = new Service() {
        // @Override
        // public boolean serve() {

        // return false;
        // }
        // };
        // service.pace.set(1L);
        // service.start();
    }

    @Override
    public String getIdentity() {
        return "Euler Diagram";
    }

    @SuppressWarnings("unchecked")
    @Override
    public Element<T> findElementAt(Point point) {
        for (Representative r : representatives) {
            if (r.shape.contains(point)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        int x, y;
        for (Representative r : representatives) {
            g.draw(r.shape);
            x = (int) (diameter + 4 + r.shape.getX());
            y = (int) (diameter - 4 + r.shape.getY());
            g.drawString(r.model.toString(), x, y);
        }
    }

    @Override
    public void _refresh() {
        System.out.println("Refreshed");
    }

}
