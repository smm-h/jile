package jile.vis;

import java.awt.Color;
import java.awt.Insets;

public class JFrame extends javax.swing.JFrame {

    public JFrame(String title) {
        super(title);
    }

    private Insets insets;

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b)
            this.insets = getInsets();
    }

    public void setInnerSize(int w, int h) {
        setSize(w + insets.left + insets.right, h + insets.top + insets.bottom);
    }

    public int getInnerX() {
        return getX() + insets.left;
    }

    public int getInnerY() {
        return getY() + insets.top;
    }

    public int getInnerWidth() {
        return getWidth() - insets.left - insets.right;
    }

    public int getInnerHeight() {
        return getHeight() - insets.top - insets.bottom;
    }

    // @Override
    // public boolean isOptimizedDrawingEnabled() {
    // return false;
    // }

    @Override
    public void setBackground(Color bgColor) {
        getContentPane().setBackground(bgColor);
    }

    @Override
    public Color getBackground() {
        return getContentPane().getBackground();
    }
}
