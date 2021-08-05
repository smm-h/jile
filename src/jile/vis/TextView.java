package jile.vis;

// import java.awt.Color;
// import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
// import java.awt.Stroke;
import java.util.ArrayList;

import jile.lingu.Code;
import jile.lingu.IndividualTokenType.IndividualToken;
import jile.lingu.Tokenizer;
import jile.lingu.Setable;
import jile.lingu.TextLanguage;
import jile.common.Convert;

public class TextView extends View {

    public static void main(String[] args) {
        Viewer.singleton().show(new TextView(
                "Lorem ipsum dolor sit amet, `consectetur adipiscing elit`, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. `Ut enim ad minim veniam`, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. `Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.` Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
    }

    // public final Setable<Fabric> fabric_back = new Setable<Fabric>(this,
    // "back.fabric", new Fabric(0.8f, 0.5f));
    // public final Setable<Fabric> fabric_border = new Setable<Fabric>(this,
    // "border.fabric", new Fabric(0.4f, 0.5f));
    public final Setable<Fabric> fabric_text = new Setable<Fabric>(this, "text.fabric", new Fabric(0f));

    private ParrotGraphics parrot;

    public final Setable<Float> padding_t = new Setable<Float>(this, "padding.top", 4f);
    public final Setable<Float> padding_l = new Setable<Float>(this, "padding.left", 12f);
    public final Setable<Float> padding_r = new Setable<Float>(this, "padding.right", 12f);
    public final Setable<Float> padding_b = new Setable<Float>(this, "padding.bottom", 14f);
    public final Setable<Float> border_width = new Setable<Float>(this, "border.width", 1f);
    public final Setable<Float> m_w = new Setable<Float>(this, "text.width", 640f);
    // public final Setable<Float> m_h = new Setable<Float>(this, "height.max",
    // 100f);

    // private static Stroke border_stroke = new BasicStroke(1f);

    private static final Scribe font_text;
    private static final Scribe font_code;

    static {
        font_text = new Scribe("Segoe UI", 18);
        font_code = new Scribe("Consolas", 18);
    }

    private final int lineHeight;

    {
        lineHeight = Math.max(font_text.getMetrics().getHeight(), font_code.getMetrics().getHeight());
    }

    // private static Color color_text = ;
    // private static Color color_code = Color.ORANGE;

    public TextView(String text) {
        super(Mode.STILL);
        backgroundFabric = new Fabric(0.9f, 0.9f);
        setText(text);
    }

    public void setText(String text) {

        parrot = new ParrotGraphics();

        Code code = new Code(text, TextLanguage.singleton());

        int e_t = (int) (padding_t.get() + border_width.get());
        int e_l = (int) (padding_l.get() + border_width.get());
        int e_r = (int) (padding_r.get() + border_width.get());
        int e_b = (int) (padding_b.get() + border_width.get());

        int maxLineWidth = (int) (m_w.get() - e_r - e_l);
        int horizontalBound = maxLineWidth + e_l;

        // drawing position
        int dx = e_l;
        int dy = e_t;
        int max_dx = 0;

        // token width and line height
        int tw = 0;
        // int lh = 0;

        ArrayList<IndividualToken> tokens = Convert.List_to_ArrayList(Tokenizer.tokenized.read(code));

        FontMetrics gear;

        boolean lineBreak = false;

        Scribe lastFont = font_text;

        parrot.new SetFont(font_text.getFont());

        for (int i = 0; i < tokens.size(); i++) {
            IndividualToken token = tokens.get(i);

            // explicit line-break
            if (token == null || token.is("newline")) {
                lineBreak = true;
            } else

            // whole tokens, like words and numbers, which are NOT safe to break
            if (token.is("word", "number", "mono")) {

                // inline code, that must be written in a mono-width font
                boolean isMono = token.is("mono");

                gear = (isMono ? font_code : font_text).getMetrics();

                // measure our token
                tw = gear.stringWidth(token.data);
                // lh = Math.max(lh, gear.getHeight());

                // if this token is single-handedly larger than the limit, we are forced
                // to break it
                if (tw > maxLineWidth) {
                    int breakingPoint = findBreakingPoint(token.data, gear, maxLineWidth);
                    tokens.set(i, token.new BrokenPiece(breakingPoint));
                    tokens.add(i, null);
                    tokens.add(i, token.new BrokenPiece(0, breakingPoint));
                    i--;
                } else

                // if not, and we can serve it wholly, if it fits in this line
                if (dx + tw < horizontalBound) {
                    if (isMono) {
                        if (lastFont != font_code) {
                            lastFont = font_code;
                            parrot.new SetFont(font_code.getFont());
                        }
                    } else {
                        if (lastFont != font_text) {
                            lastFont = font_text;
                            parrot.new SetFont(font_text.getFont());
                        }
                    }
                    parrot.new DrawText(token.data, dx, dy + lineHeight);
                    dx += tw;
                    max_dx = Math.max(max_dx, dx);
                }

                // if it can fit in a line, but not this one, then we'll try again in the next
                // line.
                else {
                    i--;
                    lineBreak = true;
                }
            }

            // non-word streaks, which are safe to break
            // if (token.is("unknown_character", "breakible", "inlinewhitespace"))
            else {

                gear = font_text.getMetrics();

                // measure our token
                tw = gear.stringWidth(token.data);
                // lh = Math.max(lh, gear.getHeight());

                // if this token is single-handedly larger than the limit, we are forced
                // to break it
                if (tw > maxLineWidth) {
                    int breakingPoint = findBreakingPoint(token.data, gear, maxLineWidth);
                    tokens.set(i, token.new BrokenPiece(breakingPoint));
                    tokens.add(i, null);
                    tokens.add(i, token.new BrokenPiece(0, breakingPoint));
                    i--;
                } else

                // if not, and we can serve it wholly, if it fits in this line
                if (dx + tw < horizontalBound) {
                    if (lastFont != font_text) {
                        lastFont = font_text;
                        parrot.new SetFont(font_text.getFont());
                    }
                    parrot.new DrawText(token.data, dx, dy + lineHeight);
                    dx += tw;
                    max_dx = Math.max(max_dx, dx);
                }

                // if it can fit in a line, but not this one, then we'll try again in the next
                // line.
                else {
                    i--;
                    lineBreak = true;
                }
            }

            if (lineBreak) {
                lineBreak = false;
                dy += lineHeight;
                // lh = 0;
                dx = e_l;
                while (tokens.get(i + 1).is("inlinewhitespace")) {
                    i++;
                }
            }
        }

        dy += lineHeight;

        setSize(max_dx + e_r, dy + e_b);
        // setSize((int) Math.min(m_w.get(), max_dx + e_r), (int) Math.min(m_h.get(), dy
        // + e_b));
    }

    /** {@code w} must not be zero, and {@code string} must not be empty. */
    private int findBreakingPoint(String string, FontMetrics metrics, int w) {
        int x = 0;
        int i = 0;
        while (x < w && i < string.length()) {
            x += metrics.charWidth(string.charAt(i++));
        }
        return i - 1;
    }

    @Override
    public String getIdentity() {
        return "TextView";
    }

    @Override
    public Element<Object> findElementAt(Point point) {
        return null;
    }

    @Override
    public void draw(Graphics2D g) {
        // int w = getWidth();
        // int h = getHeight();

        // g.setStroke(border_stroke);
        // g.setColor(fabric_back.get().toColor());
        // g.fillRect(0, 0, w, h);

        // g.setColor(fabric_border.get().toColor());
        // g.drawRect(0, 0, w - 1, h - 1);

        g.setColor(fabric_text.get().toColor());
        parrot.beDrawnOn(g);
    }

    @Override
    public void _refresh() {
    }
}
