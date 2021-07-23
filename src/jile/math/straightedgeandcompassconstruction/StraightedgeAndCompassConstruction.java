package jile.math.straightedgeandcompassconstruction;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jile.math.numbers.Real;

abstract public class StraightedgeAndCompassConstruction implements Iterable<Drawing> {

    private int w, h;
    private final List<Drawing> drawings;

    private final Frame frame;
    private final Canvas canvas;
    private final Insets insets;
    private static final int padding = 32;
    private static final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    public StraightedgeAndCompassConstruction() {
        drawings = new LinkedList<Drawing>();
        frame = new Frame("Straightedge & Compass Construction");

        // respond to close button event
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
                System.exit(0);
            }
        });

        // create the canvas
        canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                g.setColor(Color.DARK_GRAY);
                for (Drawing drawing : drawings) {
                    drawing.draw(g);
                }
            }
        };

        // background color
        Color bgc = new Color(0xEEEEEE);
        canvas.setBackground(bgc);

        frame.add(canvas);
        frame.setLayout(null);
        frame.setBackground(Color.GRAY);
        frame.setVisible(true);
        insets = frame.getInsets();
    }

    public void setSize(int w, int h) {

        drawings.clear();

        this.w = w;
        this.h = h;

        // canvas dimensions
        int cw = w;
        int ch = h;

        // frame dimensions
        int fw = w + padding * 2;
        int fh = h + padding * 2;

        // screen dimensions
        int sw = (int) screen.getWidth();
        int sh = (int) screen.getHeight();

        frame.setLocation((sw - fw) / 2, (sh - fh) / 2);
        canvas.setSize(cw, ch);
        canvas.setLocation((fw - cw) / 2 + insets.left, (fh - ch) / 2 + insets.top);
        frame.setSize(fw + insets.left + insets.right, fh + insets.top + insets.bottom);
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    @Override
    public Iterator<Drawing> iterator() {
        return drawings.iterator();
    }

    public void add(Drawing c) {
        drawings.add(c);
        canvas.repaint();
        frame.repaint();
    }

}