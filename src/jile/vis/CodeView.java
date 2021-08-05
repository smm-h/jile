package jile.vis;

import java.util.*;

import jile.lingu.Code;
import jile.lingu.Mishap;
import jile.lingu.IndividualTokenType;
import jile.lingu.Languages;
import jile.lingu.IndividualTokenType.IndividualToken;
import jile.lingu.Codestack;
import jile.lingu.TextLanguage;
import jile.common.Common;
import jile.common.Resource;
import jile.common.Singleton;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.BasicStroke;

// import java.awt.font.FontRenderContext;
// import java.awt.font.TextLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// @SuppressWarnings("serial")
public class CodeView extends View {
    private final List<TokenView> tokenViews = new LinkedList<TokenView>();
    private final Code code;

    private final FontMetrics fontMetrics;
    private final int lineHeight, lineAscent, lineDescent, characterWidth;
    private int padding = 24;

    // private static final String FONT_NAME = "Consolas";
    // private static final String FONT_NAME = "Source Code Pro";
    private static final String FONT_NAME = "Courier New";
    private static final int FONT_SIZE = 18;

    private final Set<TokenView> linkies = new HashSet<TokenView>();
    // private final Set<TokenView> baddies = new HashSet<TokenView>();
    // private final Set<TokenView> hinties = new HashSet<TokenView>();

    public static void main(String[] args) {
        Codestack.highlightAndShow("nlx/tree-language.nlx");
    }

    public static Scribe makeScribe(Dye dye, Scribe.Style style) {
        return new Scribe(FONT_NAME, FONT_SIZE, foregroundFabric, dye, style);
    }

    private static final Fabric foregroundFabric = new Fabric(1);

    @Override
    public void _refresh() {
        // nothing to do, really
    }

    private Stroke dashed = Views.makeDashedStroke(1, 4);

    private int lineNumbersX;

    @Override
    public void draw(Graphics2D g) {
        // graphics.setColor(new Color(255, 222, 100));
        // graphics.fillRect(0, 0, getWidth(), getHeight());
        g.setStroke(dashed);
        g.setColor(scribeOfLineNumber.fabric.toColor());
        g.drawLine(lineNumbersX, padding / 2, lineNumbersX, getHeight() - padding / 2);
        for (TokenView tokenView : tokenViews) {
            if (tokenView != null) {
                tokenView.draw(g);
            }
        }

        // System.out.println("\n");
    }

    private final LineNumber ln = new LineNumber();

    private static Scribe scribeOfLineNumber = new Scribe(FONT_NAME, FONT_SIZE, new Fabric(0.5f, 0.5f), null,
            Scribe.Style.NEITHER);

    private class LineNumber extends IndividualTokenType implements Singleton {

        private LineNumber() {
            super("LINE_NUMBER");
            setScribe(scribeOfLineNumber);
        }

        class LineNumberToken extends IndividualToken {
            public LineNumberToken() {
                super(Common.fill(Common.stringOfValue(++lineNumber), " ", true, 3, false) + "   ", -1);
            }
        }
    }

    private void addLineNumber() {
        addToken(ln.new LineNumberToken());
    }

    private int tx = padding, ty = padding, mx = 0, lineNumber = 0;

    private void addToken(IndividualToken token) {
        String text = token.getData();

        // if the token we are adding contains a line break
        if (text.contains("\n")) {

            // find the first one
            int index = text.indexOf('\n');

            // add whatever is before it, if anything, as a broken token
            if (index > 0)
                addToken(token.new BrokenPiece(0, index));

            // add line break signal
            // tokenViews.add(null);

            // actually break the line
            ty += lineHeight;
            tx = padding;

            // add the new line's number
            addLineNumber();

            // add whatever is after the first line break, if anything
            // this itself may contain more line breaks
            if (text.length() - index > 1)
                addToken(token.new BrokenPiece(index + 1));

        } else {
            tokenViews.add(new TokenView(token));
            mx = Math.max(mx, tx);
            // if (tx > 1000) {
            // tx = 0;
            // ty += lineHeight;
            // } // WORD WRAP
        }
    }

    @SuppressWarnings("unchecked") // TODO why does this warning generate
    @Override
    public Element<IndividualToken> findElementAt(Point point) {
        for (TokenView v : tokenViews)
            if (v.shape.contains(point)) // v != null &&
                return v;
        return null;
    }

    public CodeView(View.Mode mode, Code code) {

        super(mode);

        Objects.requireNonNull(code);
        this.code = code;

        backgroundFabric = new Fabric(0.9f, 0.9f);

        getComponent().addMouseListener(new MouseAdapter() {

            Viewer v = Viewer.singleton();

            @Override
            public void mouseClicked(MouseEvent mouse) {

                // the user just clicked on something, let's find out what
                boolean fulfilled = false;
                // and fulfill their intention

                // they may have clicked on a link
                if (!fulfilled) {
                    for (TokenView tokenView : linkies) {
                        if (tokenView.shape.contains(mouse.getPoint())) {
                            if (Languages.singleton().getLanguageByExt(tokenView.resource.getExt()) != null) {
                                v.show(new Code(tokenView.resource).visualize());
                            } else {
                                v.show(new Code(tokenView.resource, TextLanguage.singleton()).visualize());
                            }
                            fulfilled = true;
                            break;
                        }
                    }
                }
            }
        });

        getComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ADD)
                    increaseFontSize();
                if (e.getKeyCode() == KeyEvent.VK_SUBTRACT)
                    decreaseFontSize();
            }
        });

        fontMetrics = new Scribe(FONT_NAME, FONT_SIZE).getMetrics();
        lineHeight = fontMetrics.getHeight();
        lineAscent = fontMetrics.getAscent();
        lineDescent = fontMetrics.getDescent();
        characterWidth = fontMetrics.stringWidth("a");
        lineNumbersX = padding + (int) (characterWidth * 4.5);

        // g.setRenderingHint(RenderingHints.KEY_RENDERING,
        // RenderingHints.VALUE_RENDER_QUALITY);

        _links = Code.links.read(code);
        // if (links == null) links = Collections.emptyMap();

        _mishaps = Code.mishaps.read(code);
        // if (mishaps == null) mishaps = Collections.emptyMap();

        var tokens = Objects.requireNonNull(Code.syntax.read(code));

        addLineNumber();

        for (IndividualToken token : tokens)
            addToken(token);

        ty += lineHeight;

        setSize(mx + padding, ty + padding);
    }

    private final Map<IndividualToken, Resource> _links;
    private final Map<IndividualToken, Set<Mishap>> _mishaps;

    private void decreaseFontSize() {
        // TODO
    }

    private void increaseFontSize() {
        // TODO
    }

    private class TokenView extends View.Element<IndividualToken> implements Hintful {

        final IndividualToken model;
        final Shape shape;
        final Scribe scribe;
        final String text;
        final Resource resource;
        final Set<Mishap> mishaps;

        final int x, y, w;

        @Override
        public IndividualToken getModel() {
            return model;
        }

        private Set<Stamp> stamps = null;

        private void addStamp(Stamp stamp) {
            if (stamps == null)
                stamps = new HashSet<Stamp>();
            stamps.add(stamp);
        }

        public TokenView(IndividualToken model) {
            this.model = model;
            this.text = model.getData();
            this.scribe = model.getScribe(); // != null ? model.getScribe() : scribeOfLineNumber;
            this.resource = _links == null ? null : _links.get(model);
            this.mishaps = _mishaps == null ? null : _mishaps.get(model);
            x = tx;

            /*
             * Three methods of measuring the width of a text drawn in a mono-wdith font are
             * listed below: char width * char count, SwingUtilities.computeStringWidth, and
             * fontMetrics.stringWidth. All three work, but since the font is mono-width,
             * the first one is the most efficient.
             */
            w = characterWidth * text.length();
            // w = SwingUtilities.computeStringWidth(fontMetrics, text);
            // w = fontMetrics.stringWidth(text);

            // System.out.print("(" + w + ")");
            tx += w;
            y = ty;
            // shape = new Rectangle(x + 1, y + 1, w - 2, lineHeight - 2);
            shape = new Rectangle(x, y, w, lineHeight);

            int uly = y + lineAscent + lineDescent;

            if (resource != null) {
                if (resource.exists()) {

                    // pale blue dashed underline for links
                    addStamp(new Stamp(new Line2D.Float(x, uly, x + w, uly), new Color(0, 40, 72, 172),
                            Views.makeDashedStroke(1, 4)));

                    linkies.add(this);
                } else {
                    System.out.println("Resource does not exist: " + resource.getIdentity());
                }
            }

            if (mishaps != null && !mishaps.isEmpty()) {

                // hot red squiggly underline for mishaps
                addStamp(new Stamp(Views.makeHorizontalSquiggle(x, x + w, uly, 3.5f, 2), Color.RED,
                        new BasicStroke(0.9f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL)));

                // new Color(172, 40, 72)

                // baddies.add(this);
                // hinties.add(this);

            }
        }

        @Override
        public String toString() {
            return text;
        }

        public void draw(Graphics graphics) {

            if (scribe != null) {

                Graphics2D g = (Graphics2D) graphics;

                // draw background
                if (isMouseInside()) {
                    // System.out.print(".");
                    g.setStroke(new BasicStroke(2.5f));
                    g.setColor(new Color(0.15f, 0.85f, 0.85f, 0.15f));
                    g.draw(shape);
                }

                // draw shadow
                scribe.applyOn(g);
                g.setColor(new Color(0, 0, 0, 0.1f));
                g.drawString(text, x + 0.0f, y + lineAscent + 1.0f);
                g.drawString(text, x + 0.2f, y + lineAscent + 0.7f);
                g.drawString(text, x + 0.4f, y + lineAscent + 0.4f);
                g.drawString(text, x + 0.6f, y + lineAscent + 0.1f);

                // draw text
                scribe.applyOn(g);
                g.drawString(text, x, y + lineAscent);

                // g.draw(text);

                if (stamps != null)
                    for (Stamp stamp : stamps)
                        stamp.stampOn(g);
            }
        }

        @Override
        public Hint getHint() {

            if (mishaps != null) {
                StringJoiner joiner = new StringJoiner("\n").setEmptyValue("False alarm.");
                for (Mishap m : mishaps)
                    joiner.add(m.getReport());
                return new TextualHint(joiner.toString());
            } else
                return null;

        }

        @Override
        public Shape getShape() {
            return shape;
        }
    }

    @Override
    public String getIdentity() {
        return "CodeView of " + code.getIdentity();
    }

}