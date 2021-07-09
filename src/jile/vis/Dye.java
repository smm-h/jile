package jile.vis;

import java.awt.Color;

/**
 * An immutable class denoting an specific shade of a specific color. If a dye
 * is naturally dark, it will be light in dark mode, and vice-versa. Coupled
 * with {@link Fabric}
 */
public class Dye {

    /** Between 0 and 1. */
    private final float hue;

    /**
     * Between -1 and 1. -1 is completely white, +1 is completely black. 0 means
     * fully saturated hue.
     */
    private final float shade;

    public Dye(float hue) {
        this(hue, 0);
    }

    public float getHue() {
        return hue;
    }

    public float getShade() {
        return getShade(false);
    }

    public float getShade(boolean contrast) {
        float v = Views.darkMode ? -shade : shade;

        if (contrast) {

            // s = (-0.5, +0.5) => saturate
            if (v > -0.5f || v < 0.5f)
                // -0.5~+0.5 -> -0.1~+0.1
                v /= 5;

            // s = [+0.5, +1] => brighten
            else if (v > 0.5f)
                // 0.5~1.0 -> 1.0~0.9
                v = +1 - (v - 0.5f) / 5;

            // s = [-1, -0.5] => darken
            else
                // -1.0~-0.5 -> -0.9~-1
                v = -1 - (v + 0.5f) / 5;

        }

        return v;
    }

    public Dye(float hue, float shade) {
        this.hue = hue;
        this.shade = shade;
    }

    /** Make a dye from a color that is assumed to be in light-mode. */
    public static Dye fromColor(Color color) {

        if (color == null)
            return null;

        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

        // y\x 1 ~0.5 0
        // -1 -1 -0.5 0
        // ~0 ~0 ~0 ~~0
        // +1 +1 +0.5 0
        // z = x*y = (1-s)*(v*2-1)

        return new Dye(hsb[0], (1 - hsb[1]) * (hsb[2] * 2 - 1));
        // Some information is lost, therefore a Dye should not be used to make
        // meaningful colors.
    }
}
