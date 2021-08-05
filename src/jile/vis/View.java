package jile.vis;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

import java.awt.Point;

import javax.swing.JPanel;

import jile.lingu.Code;
import jile.lingu.TreeLanguage;
import jile.common.Identifiable;

// @SuppressWarnings("serial")
abstract public class View implements Identifiable, Visualizable {

    public static void main(String[] args) {
        Code code = new Code("\"a\"", TreeLanguage.singleton());
        Viewer.singleton().show(code.visualize());
        var made = (Visualizable) TreeLanguage.singleton().getMainMaker().make(code);
        Viewer.singleton().show(made.visualize());
    }

    public enum Mode {
        /**
         * no internal movement, no core data mutation, no hints, no
         * selection/colorization
         * 
         * useful e.g. when showing a view as a hint
         * 
         * all views must support this mode
         */
        STILL,
        /**
         * elements may be statelessly moved and/or selected and/or colorized, however
         * no core data mutation
         * 
         * all views must support this mode, at least identical to STILL
         */
        INTERACTIVE,
        /**
         * elements and core data may be statefully mutated
         * 
         * views may opt to not support this mode
         */
        EDITING,
    }

    public final Mode mode;

    private final JPanel component;

    public View(Mode mode) {
        component = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                // super.paintComponent(g);
                View.this.paintComponent(g);
            }
        };
        component.setOpaque(false);
        this.mode = mode;
    }

    /**
     * Find out which of your {@link Element} elements contain the given
     * {@code point} and return it.
     */
    abstract public <Model> Element<Model> findElementAt(Point point);

    abstract public class Element<Model> {

        abstract public Model getModel();

        abstract public Shape getShape();

        private boolean isMouseInside = false;

        public void setMouseInside(boolean isMouseInside) {
            this.isMouseInside = isMouseInside;
        }

        public boolean isMouseInside() {
            return isMouseInside;
        }

        public View getView() {
            return View.this;
        }
    }

    private boolean dirty = true;

    public boolean isDirty() {
        return dirty;
    }

    public void beTainted() {
        this.dirty = true;
        Viewer.singleton().refresh();
    }

    // @Override
    private final void paintComponent(Graphics graphics) {
        refresh();
        var g = (Graphics2D) graphics;
        Views.applyCoolness(g);
        float arc = 16; // Math.max(8, Math.min(w, h) / 8);
        var s = new RoundRectangle2D.Float(0f, 0f, getWidth() - 1, getHeight() - 1, arc, arc);
        g.setColor(backgroundFabric.toColor());
        g.fill(s);
        g.setColor(outlineFabric.toColor());
        g.setStroke(outlineStroke);
        g.draw(s);
        if (scribe != null)
            g.setFont(scribe.getFont());
        draw(g);
    }

    public Fabric backgroundFabric = new Fabric(1, 0.5f);

    public static final Fabric outlineFabric = new Fabric(0.5f, 0.67f);

    public static final Stroke outlineStroke = Views.makeDashedStroke(1, 4);

    private Scribe scribe;

    public void setScribe(Scribe scribe) {
        this.scribe = scribe;
        beTainted();
    }

    public Scribe getScribe() {
        return scribe;
    }

    abstract public void draw(Graphics2D g);

    abstract public void _refresh();

    public final void refresh() {
        if (dirty) {
            dirty = false;
            _refresh();
            Viewer.updateThumbnail(this);
        }
    }

    @Override
    public final View visualize() {
        return this;
    }

    public Image getImage() {
        int w = getWidth(), h = getHeight();
        var image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        var g = (Graphics2D) image.getGraphics();
        // new Color(0, 0, 0, 0)
        // g.setBackground(new Color(255, 255, 255, 0));
        // g.setComposite(AlphaComposite.Clear);
        g.setColor(Viewer.singleton().getBackground());
        g.fillRect(0, 0, w, h);
        // TODO the copied image will be in light mode regardless of Views.darkMode
        // g.clearRect(0, 0, w, h);
        // g.setComposite(AlphaComposite.SrcOver);
        Views.applyCoolness(g);
        if (scribe != null)
            g.setFont(scribe.getFont());
        draw(g);
        // paintComponent(g);
        return image;
        // return Views.blur(image);
    }

    public int getHeight() {
        return component.getHeight();
    }

    public int getWidth() {
        return component.getWidth();
    }

    public void setSize(int w, int h) {
        component.setSize(w, h);
    }

    public void setLocation(Point point) {
        setLocation(point.getX(), point.getY());
    }

    public void setLocation(double x, double y) {
        setLocation((int) x, (int) y);
    }

    // @Override
    public void setLocation(int x, int y) {
        // x = Math.max(-getWidth(), Math.min(x, (int) Views.screenSize.getWidth()));
        // y = Math.max(-getHeight(), Math.min(y, (int) Views.screenSize.getHeight()));

        int p = Viewer.singleton().padding;
        int kx = Viewer.singleton().getInnerWidth() - getWidth() - p;
        int ky = Viewer.singleton().getInnerHeight() - getHeight() - p;

        if (kx >= 0)
            x = Math.min(kx, Math.max(x, p));
        else
            x = Math.min(p, Math.max(x, kx));
        if (ky >= 0)
            y = Math.min(ky, Math.max(y, p));
        else
            y = Math.min(p, Math.max(y, ky));

        component.setLocation(x, y);
        refreshRelativePositions();
    }

    private void refreshRelativePositions() {
        for (Relative r : relatives) {
            r.originMoved();
        }
    }

    public interface Relative {
        public void originMoved();
    }

    private Set<Relative> relatives = new HashSet<Relative>();

    public void moveNearElement(Element<?> element) {
        refresh();
        int w = getWidth();
        int h = getHeight();
        var bounds = element.getShape().getBounds();
        var point = bounds.getLocation();
        int bh = (int) bounds.getHeight() + 4;
        int x = element.getView().component.getX() + (int) point.getX() + (int) bounds.getWidth() - w / 2;
        int y = element.getView().component.getY() + (int) point.getY() + bh;

        int mw = Viewer.singleton().getInnerWidth();
        int mh = Viewer.singleton().getInnerHeight();

        if (x + w > mw)
            x = (int) mw - w - 8;

        if (y + h > mh)
            y = (int) mh - h - bh;

        if (x < 0)
            x = 8;

        if (y < 0)
            y = bh;

        setLocation(x, y);
    }

    public int getX() {
        return component.getX();
    }

    public int getY() {
        return component.getY();
    }

    public Point getLocation() {
        return component.getLocation();
    }

    public Component getComponent() {
        return component;
    }
}