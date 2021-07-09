package jile.vis;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

public class Stamp {
    private final Shape shape;
    private final Color color; // TODO rethink this design
    private final Stroke stroke;

    public Stamp(Shape shape, Color color, Stroke stroke) {
        this.shape = shape;
        this.color = color;
        this.stroke = stroke;
    }

    public void stampOn(Graphics2D g) {
        g.setStroke(stroke);
        g.setColor(color);
        g.draw(shape);
    }
}