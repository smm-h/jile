package jile.vis;

import java.awt.*;
import java.awt.geom.*;

import jile.common.Random;

public class Views {

    public static boolean darkMode = false;

    public static void toggleDarkMode() {
        darkMode = !darkMode;
    }

    public static final Dye accent = new Dye(Random.singleton().nextFloat(), 1f);

    public static enum Connector {
        STRAIGHT, SQUARED, SAWED, CURVE, SERIOUS_CURVE, SQUIGGLY, QUAD_1, QUAD_2
    }

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void applyCoolness(Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public static Stroke makeStroke(float width) {
        return new BasicStroke(width);
    }

    public static Stroke makeDashedStroke(float width, float length) {
        return new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[] { length }, 0);
    }

    public static Paint makeGradientPaint(float x1, float y1, float x2, float y2, Color c1, Color c2) {
        return new GradientPaint(x1, y1, c1, x2, y2, c2);
    }

    public static Path2D.Float makeVerticalConnector(float x1, float y1, float x2, float y2, Connector type) {

        var path = new Path2D.Float();

        path.moveTo(x1, y1);

        float ym = (y1 + y2) / 2;

        float h = y2 - y1;

        switch (type) {
            case SQUARED:
                path.lineTo(x1, ym);
                path.lineTo(x2, ym);
                path.lineTo(x2, y2);
                break;
            case SAWED: {
                float cutoff = 0.5f;
                float length = Math.signum(x2 - x1) * h * cutoff;
                if (Math.abs(Angle.fromLine(x1, y1, x2, y2).getDegrees() - 90) < 90 * cutoff) {
                    path.lineTo(x2, y2);
                } else {
                    path.lineTo(x1 + length, ym);
                    path.lineTo(x2 - length, ym);
                    path.lineTo(x2, y2);
                }
            }
                break;
            case CURVE:
                path.curveTo(x1, ym, x2, ym, x2, y2);
                break;
            case SERIOUS_CURVE:
                float seriousness = 0.4f;
                path.lineTo(x1, y1 + h * seriousness);
                path.curveTo(x1, ym, x2, ym, x2, y2 - h * seriousness);
                path.lineTo(x2, y2);
                break;
            case SQUIGGLY: {
                float sign = Math.signum(x2 - x1);
                float length = sign * h * 2;
                if (sign == 0) {
                    path.lineTo(x2, y2);
                } else {
                    path.curveTo(x1 + length, ym, x2 - length, ym, x2, y2);
                }
            }
                break;
            case QUAD_1:
                path.quadTo(x1, ym, x2, y2);
                break;
            case QUAD_2:
                path.quadTo(x2, ym, x2, y2);
                break;
            case STRAIGHT:
                path.lineTo(x2, y2);
                break;
        }

        return path;
    }

    public static Path2D makeHorizontalSquiggle(float startx, float endx, float y, float w, float h) {
        h /= 2;
        var path = new Path2D.Float();
        path.moveTo(startx, y - h);
        while (true) {
            path.lineTo(startx += w, y + h);
            if (startx >= endx)
                break;
            path.lineTo(startx += w, y - h);
            if (startx >= endx)
                break;
        }
        return path;
    }

    public static Color mergeRGB1(Color c1, Color c2, float p1) {

        if (p1 > 1f)
            p1 = 1f;
        else if (p1 < 0f)
            p1 = 0f;

        float p2 = 1.0f - p1;

        int r = (int) (c1.getRed() * p1 + c2.getRed() * p2);
        int g = (int) (c1.getGreen() * p1 + c2.getGreen() * p2);
        int b = (int) (c1.getBlue() * p1 + c2.getBlue() * p2);

        return new Color(r, g, b);
    }

    public static Color mergeRGBA(Color c1, Color c2, float p1) {

        if (p1 > 1f)
            p1 = 1f;
        else if (p1 < 0f)
            p1 = 0f;

        float p2 = 1.0f - p1;

        int r = (int) (c1.getRed() * p1 + c2.getRed() * p2);
        int g = (int) (c1.getGreen() * p1 + c2.getGreen() * p2);
        int b = (int) (c1.getBlue() * p1 + c2.getBlue() * p2);
        int a = (int) (c1.getAlpha() * p1 + c2.getAlpha() * p2);

        return new Color(r, g, b, a);
    }

    public static int mergeRGB(int c1, int c2, float p1) {

        if (p1 > 1f)
            p1 = 1f;
        else if (p1 < 0f)
            p1 = 0f;

        float p2 = 1.0f - p1;

        int r = (int) (((c1 >> 16) & 0xFF) * p1 + ((c2 >> 16) & 0xFF) * p2);
        int g = (int) (((c1 >> 8) & 0xFF) * p1 + ((c2 >> 8) & 0xFF) * p2);
        int b = (int) ((c1 & 0xFF) * p1 + (c2 & 0xFF) * p2);

        return ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
    }

    public static int mergeRGBA(int c1, int c2, float p1) {

        if (p1 > 1f)
            p1 = 1f;
        else if (p1 < 0f)
            p1 = 0f;

        float p2 = 1.0f - p1;

        int a = (int) (((c1 >> 24) & 0xFF) * p1 + ((c2 >> 24) & 0xFF) * p2);
        int r = (int) (((c1 >> 16) & 0xFF) * p1 + ((c2 >> 16) & 0xFF) * p2);
        int g = (int) (((c1 >> 8) & 0xFF) * p1 + ((c2 >> 8) & 0xFF) * p2);
        int b = (int) ((c1 & 0xFF) * p1 + (c2 & 0xFF) * p2);

        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
    }
}