package jile.vis;

import java.awt.Color;

/**
 * An immutable class denoting a gray semi-transparent colorless texture.
 * Coupled with {@link Dye}, they may be used to make {@link Color}s that
 * correctly and meaningfully responds to different situations such as "dark
 * mode" (e.g. {@link Views#darkMode}) or "being selected". In comparison to
 * {@link Color}, these two classes are a better choice for designing
 * user-interface elements.
 */
public class Fabric {

    private static final float HALF = 0.5f;

    /**
     * A single shade of gray that will remain un-affected under any and all
     * circumstances
     */
    public static final Fabric indifferentGray = new Fabric(HALF);

    /** Between 0 and 1. */
    private final float brightness, transparency;

    public Fabric(float brightness) {
        this(brightness, 1);
    }

    public Fabric(float brightness, float transparency) {
        this.brightness = brightness;
        this.transparency = transparency;
    }

    /** Make a fabric from a color that is assumed to be in light-mode. */
    public static Fabric fromColor(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return new Fabric(hsb[2], color.getAlpha() / 255f);
        // Some information is lost, therefore a Fabric alone makes shades of gray.
    }

    public Color toColor() {
        return toColor(null, false);
    }

    public Color toColor(boolean contrast) {
        return toColor(null, contrast);
    }

    public Color toColor(Dye dye) {
        return toColor(dye, dye != null);
    }

    public Color toColor(Dye dye, boolean contrast) {

        // indifferent gray
        if (brightness == HALF)
            return new Color(HALF, HALF, HALF, transparency);

        boolean grayscale;
        if (dye == null) {
            grayscale = true;
            dye = Views.accent;
        } else {
            grayscale = false;
            // TODO somehow mix dye with Views.accent
        }

        // System.out.print(grayscale ? "X" : "O");

        if (grayscale) {

            float b = brightness;

            if (contrast)
                if (b > 0.5f)
                    b = 1 - ((1 - b) / 5);
                else
                    b /= 5;

            b = Views.darkMode ? 1 - b : b;
            b = contrast ? 1 - b : b;

            return new Color(b, b, b, transparency);

        } else {

            int alpha = ((int) transparency * 255) << 24;

            float v = dye.getShade(contrast);

            boolean white;

            if (v > 0) {
                white = !Views.darkMode;
            } else {
                v = -v;
                white = Views.darkMode;
            }

            // here, v is either fully hue (v=0) or fully b/w (v=1)

            return new Color(
                    alpha | Views.mergeRGB(white ? 16777215 : 0, Color.HSBtoRGB(dye.getHue(), 1f, brightness), v));

        }
        // float h = accent == -1 ? hue : accentuateHue(hue, accent);
        // float s = Views.darkMode ? Math.min(1, saturation * 0.8f) : saturation;
        // float b = Views.darkMode ? Math.min(1, brightness * 1.5f) : brightness;
        // return Color.getHSBColor(h, s, b);
    }

    // private static float accentuateHue(float source, float accent) {
    // // float hue = source;

    // // // calculate the two distances: leftwards and rightwards (starting from
    // // source)
    // // float leftwards = Math.abs(accent - source);
    // // float rightwards = 1 - leftwards;
    // // float distance = Math.min(leftwards, rightwards);
    // // return hue;
    // return accent;
    // }
}
