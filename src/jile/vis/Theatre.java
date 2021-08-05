package jile.vis;

import java.awt.*;
import java.awt.Point;
import java.awt.image.BufferedImage;

import jile.lingu.Setable;
import jile.common.Service;

public class Theatre extends View {

    private static String sample = "Hello, world!";

    public static void main(String[] args) {
        var p = new ParrotGraphics();
        var v = p.visualize();
        var s = new Scribe("Segoe UI", 36, Scribe.Style.ITALIC);
        var m = s.getMetrics();
        int x = 32;
        int y = 96;
        p.new SetColor(Color.GRAY);
        p.new SetFont(s.getFont());
        for (char c : sample.toCharArray()) {
            p.new DrawText(Character.toString(c), x, y);
            x += m.charWidth(c);
        }
        Viewer.singleton().show(v);
    }

    // source
    private final Scene scene;

    // state
    private BufferedImage image;
    private Graphics2D onImage;
    private int pointer;

    // settings
    public final Setable<Boolean> keepLooping = new Setable<Boolean>(this, "keep_looping", true);
    public final Setable<Long> pace;

    public Theatre(Scene scene, double width, double height) {
        this(scene, (int) Math.ceil(width), (int) Math.ceil(height));
    }

    public Theatre(Scene scene, int width, int height) {
        super(Mode.INTERACTIVE);
        this.scene = scene;
        setSize(width, height);
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        onImage = image.createGraphics();
        Views.applyCoolness(onImage);
        var s = new Service(Math.min(250, 4000f / scene.getSize())) {
            @Override
            public boolean serve() {
                // System.out.print(".");
                if (pointer == scene.getSize()) {
                    if (keepLooping.get()) {
                        pointer = 0;
                        onImage.setBackground(new Color(0, 0, 0, 0));
                        onImage.clearRect(0, 0, width, height);
                        beTainted();
                    } else {
                        return true;
                    }
                }
                if (!isDirty()) {
                    scene.diff(onImage, pointer++);
                    Viewer.singleton().refresh();
                }
                return false;
            }
        };
        // expose setting
        pace = s.pace;
        s.start();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void _refresh() {
        // System.out.print("R");
    }

    @Override
    public <Model> Element<Model> findElementAt(Point point) {
        return null;
        // TODO turtles
    }

    @Override
    public String getIdentity() {
        return scene.getIdentity() + " (animated)";
    }
}
