package jile.vis;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.Map;

import jile.common.*;

public class Images {

    public static Image resize(Image image, float width, float height) {
        return resize(image, (int) width, (int) height);
    }

    public static Image resize(Image image, int width, int height) {
        return resize(Convert.Image_to_BufferedImage(image), width, height);
    }

    private static Image resize(BufferedImage image, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, image.getType());
        Graphics2D g = resized.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resized;
    }

    public static Image getThumbnail(Image image, float tw, float th) {

        BufferedImage buffered = Convert.Image_to_BufferedImage(image);
        float iw = (float) buffered.getWidth();
        float ih = (float) buffered.getHeight();
        boolean Wh = iw > ih;

        float f = Wh ? tw / iw : th / ih;
        float r = Common.sqrt(iw * ih) * Common.sqrt(Wh ? iw : ih) / 10000;
        f = Math.min(f, 0.5f);
        r = Math.max(r, 1f);

        float w = f * iw;
        float h = f * ih;
        float x = (tw - w) / 2;
        float y = (th - h) / 2;

        return place(resize(blur(image, (int) r), w, h), tw, th, x, y);
    }

    public static Image place(Image image, float width, float height, float x, float y) {
        return place(image, (int) width, (int) height, (int) x, (int) y);
    }

    public static Image place(Image image, int width, int height, int x, int y) {
        return place(Convert.Image_to_BufferedImage(image), width, height, x, y);
    }

    private static Image place(BufferedImage image, int width, int height, int x, int y) {
        BufferedImage placed = new BufferedImage(width, height, image.getType());
        Graphics2D g = placed.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(image, null, x, y);
        g.dispose();
        return placed;
    }

    private static RenderingHints blurringHints;

    private static RenderingHints getBlurringHints() {
        if (blurringHints == null) {
            Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
            map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            blurringHints = new RenderingHints(map);
        }
        return blurringHints;
    }

    public static Image blur(Image image, int r) {
        return blur(Convert.Image_to_BufferedImage(image), r);
    }

    private static Image blur(BufferedImage image, int r) {
        return new ConvolveOp(getBluringKernel(r), ConvolveOp.EDGE_NO_OP, getBlurringHints()).filter(image, null);
    }

    private static final Map<Integer, Kernel> bluringKernel = new HashMap<Integer, Kernel>();

    private static Kernel getBluringKernel(int r) {

        if (!bluringKernel.containsKey(r)) {

            int w = r * 2 + 1;

            float weight = 1.0f / Common.sqr(w);
            float[] data = new float[Common.sqr(w)];
            // float m = Common.distance(0, 0, r, r);
            // float d = m * Common.sqr(w) / 2;

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < w; j++) {

                    // ugly boxy blur
                    data[i * w + j] = weight;

                    // better, round, but still ugly blur
                    // data[i * w + j] = (m - Common.distance(i, j, r, r)) / d;
                }
            }

            bluringKernel.put(r, new Kernel(w, w, data));
        }
        return bluringKernel.get(r);
    }
}
