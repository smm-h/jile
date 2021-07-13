package jile.vis;

import java.awt.*;
import java.util.*;

public class Scribe {

    public final String name;

    public final int size;

    public final Fabric fabric;

    public final Dye dye;

    public final Style style;

    public enum Style {
        NEITHER, BOLD, ITALIC, BOTH
    }

    protected static final Map<String, Map<Integer, Font[]>> cache = new HashMap<String, Map<Integer, Font[]>>();

    public Scribe(String fontname, int fontsize) {
        this(fontname, fontsize, Style.NEITHER);
    }

    public Scribe(String fontname, int fontsize, Style style) {
        this(fontname, fontsize, null, null, style);
    }

    public Scribe(String fontname, int fontsize, Fabric fabric, Dye dye, Style style) {
        this.name = fontname;
        this.size = fontsize;
        this.fabric = fabric;
        this.dye = dye;
        this.style = style;
    }

    public void applyOn(Graphics g) {

        g.setFont(getFont());

        g.setColor(fabric.toColor(dye));
    }

    public Font getFont() {
        if (!cache.containsKey(name))
            cache.put(name, new HashMap<Integer, Font[]>());

        var map = cache.get(name);

        if (!map.containsKey(size)) {
            var array = new Font[4];

            map.put(size, array);

            for (int style = 0; style < 4; style++)
                array[style] = new Font(name, style, size);
        }

        return map.get(size)[style.ordinal()];
    }

    public FontMetrics getMetrics() {
        return Viewer.singleton().getFontMetrics(getFont());
    }
}