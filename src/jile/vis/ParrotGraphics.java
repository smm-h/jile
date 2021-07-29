package jile.vis;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import jile.nilex.*;
import jile.nilex.Languages.NoLanguageAssociatedException;
import jile.nilex.Setable;

/**
 * An instance of {@code ParrotGraphics}, much like an actual parrot, will learn
 * commands and then repeat them all in the same order. It is useful when you
 * want to reproduce a drawing pattern or record some drawing commands to render
 * later, or save to a file to be read from later.
 * <p>
 * Commands include:
 * <ul>
 * <li>Setting the color, using the {@link SetColor} class</li>
 * <li>Setting the stroke, using the {@link SetStroke} class</li>
 * <li>Setting the paint, using the {@link SetPaint} class</li>
 * <li>Setting the font, using the {@link SetFont} class</li>
 * <li>Drawing a shape, using the {@link DrawShape} class</li>
 * <li>Filling a shape, using the {@link FillShape} class</li>
 * <li>Drawing text, using the {@link DrawText} class</li>
 * </ul>
 */
public class ParrotGraphics implements Decodeable, Encodeable, Visualizable, Scene {

    private final List<Command> commands = new ArrayList<Command>();

    private final Code sourceCode;

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public ParrotGraphics() {
        this((Code) null);
    }

    public ParrotGraphics(Code sourceCode) {
        this.sourceCode = sourceCode;
        clear();
    }

    public void clear() {
        commands.clear();
        minX = Double.MAX_VALUE;
        minY = Double.MAX_VALUE;
        maxX = Double.MIN_VALUE;
        maxY = Double.MIN_VALUE;
    }

    public int getSize() {
        return commands.size();
    }

    public void beDrawnOn(Graphics g) {
        beDrawnOn((Graphics2D) g);
    }

    public void beDrawnOn(Graphics2D g) {
        beDrawnOn(g, 0, commands.size());
    }

    public void beDrawnOn(Graphics2D g, int start, int count) {
        for (int i = start; i < count; i++)
            beDrawnOn(g, i);
    }

    public void beDrawnOn(Graphics2D g, int index) {
        commands.get(index).beDrawnOn(g);
    }

    @Override
    public Code getSourceCode() {
        return sourceCode;
    }

    private void updateBounds(Rectangle2D bounds) {
        // System.out.println("Before: [" + bounds.getMinX() + ", " + bounds.getMinY() +
        // ", " + bounds.getMaxX() + ", "
        // + bounds.getMaxY() + "]");
        if (Double.compare(bounds.getMinX(), Double.NaN) != 0)
            minX = Math.min(minX, bounds.getMinX());
        if (Double.compare(bounds.getMinY(), Double.NaN) != 0)
            minY = Math.min(minY, bounds.getMinY());
        if (Double.compare(bounds.getMaxX(), Double.NaN) != 0)
            maxX = Math.max(maxX, bounds.getMaxX());
        if (Double.compare(bounds.getMaxY(), Double.NaN) != 0)
            maxY = Math.max(maxY, bounds.getMaxY());
        // System.out.println("After: [" + minX + ", " + minY + ", " + maxX + ", " +
        // maxY + "]");
    }

    abstract public class Command {
        public Command() {
            commands.add(this);
        }

        abstract void beDrawnOn(Graphics2D g);
    }

    public class DrawShape extends Command {
        private final Shape shape;

        public DrawShape(Shape shape) {
            this.shape = Objects.requireNonNull(shape);
            updateBounds(shape.getBounds2D());
        }

        @Override
        void beDrawnOn(Graphics2D g) {
            g.draw(shape);
        }
    }

    public class FillShape extends Command {
        private final Shape shape;

        public FillShape(Shape shape) {
            this.shape = Objects.requireNonNull(shape);
            updateBounds(shape.getBounds2D());
        }

        @Override
        void beDrawnOn(Graphics2D g) {
            g.fill(shape);
        }
    }

    public class DrawText extends Command {
        private final String text;
        private final int x, y;

        public DrawText(String text, int x, int y) {
            this.text = Objects.requireNonNull(text);
            this.x = x;
            this.y = y;
            // updateBounds(text.getBounds2D()); TODO facilitate getting text bounds
        }

        @Override
        void beDrawnOn(Graphics2D g) {
            g.drawString(text, x, y);
        }
    }

    public class SetFont extends Command {
        private final java.awt.Font font;

        public SetFont(java.awt.Font font) {
            this.font = Objects.requireNonNull(font);
        }

        @Override
        void beDrawnOn(Graphics2D g) {
            g.setFont(font);
        }
    }

    public class SetColor extends Command {
        private final Color color;

        public SetColor(Color color) {
            this.color = Objects.requireNonNull(color);
        }

        @Override
        void beDrawnOn(Graphics2D g) {
            g.setColor(color);
        }
    }

    public class SetPaint extends Command {
        private final Paint paint;

        public SetPaint(Paint paint) {
            this.paint = Objects.requireNonNull(paint);
        }

        @Override
        void beDrawnOn(Graphics2D g) {
            g.setPaint(paint);
        }
    }

    public class SetStroke extends Command {
        private final Stroke stroke;

        public SetStroke(Stroke stroke) {
            this.stroke = Objects.requireNonNull(stroke);
        }

        @Override
        void beDrawnOn(Graphics2D g) {
            g.setStroke(stroke);
        }
    }

    @Override
    public Code encode() throws NoLanguageAssociatedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getIdentity() {
        return "Parrot Graphics" + (sourceCode == null ? "" : " of " + sourceCode.getIdentity());
    }

    public final Setable<Float> padding = new Setable<Float>(this, "padding", 16f);

    @Override
    public Theatre visualize() {
        var e = padding.get() * 2;
        var t = new Theatre(this, maxX - minX + e, maxY - minY + e);
        // t.keepLooping.set(true);
        return t;
    }

    @Override
    public void diff(Graphics2D curtains, int index) {
        if (index == 0) {
            var p = padding.get();
            curtains.translate(-minX + p, -minY + p);
        }
        beDrawnOn(curtains, index);
        if (index == getSize() - 1) {
            var p = padding.get();
            curtains.translate(minX - p, minY - p);
        }
    }
}
