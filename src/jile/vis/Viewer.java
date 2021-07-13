package jile.vis;

// import java.awt.image.BufferedImage;
import java.awt.*;
// import java.util.List;
import java.awt.Point;
import java.awt.event.*;
import java.util.*;

import javax.swing.JPanel;

import jile.nilex.Codestack;
import jile.common.*;

// @SuppressWarnings("serial")
public class Viewer extends JFrame implements Singleton {

    private static Viewer singleton;

    public static Viewer singleton() {
        if (singleton == null) {
            singleton = new Viewer();
        }
        return singleton;
    }

    public final int LAYER_VIEWS = 100;
    public final int LAYER_FOREGROUND = 500;
    public final int LAYER_HINTS = 1000;

    private final MutableTree<View> tree = new LinkedTree<View>();
    private Point panOrigin, panCurrent;

    public final int padding = 12;
    public int corner_x0 = 8;
    public int corner_y0 = 8;
    public int corner_x1;
    public int corner_y1;

    private float thumbnailFactor = 1f / 6f;
    private float thumbnailWidth = (float) (Views.screenSize.getWidth() * thumbnailFactor);
    private float thumbnailHeight = (float) (Views.screenSize.getHeight() * thumbnailFactor);

    public View getCurrentView() {
        // return stack.peek();
        return tree.getPointer();
    }

    public boolean hasView() {
        // return !stack.isEmpty();
        return !tree.isEmpty();
        // return tree.getPointer() != null;
        // TODO pointer could be null even when tree is not empty
    }

    // private boolean viewsMutated = true;

    // private List<View> leaf;

    private void push(View view) {
        // stack.push(view);
        tree.addAndGoTo(view);
        // viewsMutated = true;
        // leaf = tree.getLeaf();
    }

    private View pop() {
        // View view = stack.pop();
        View view = tree.remove();
        // viewsMutated = true;
        // leaf = tree.getLeaf();
        return view;
    }

    private final Map<View, Image> thumbnails = new HashMap<View, Image>();

    public static void updateThumbnail(View view) {
        if (singleton != null) {
            singleton._updateThumbnail(view);
        }
    }

    private void _updateThumbnail(View view) {

        if (tree.contains(view)) {

            var image = Convert.Image_to_BufferedImage(view.getImage());

            thumbnails.put(view, Images.getThumbnail(image, thumbnailWidth, thumbnailHeight));
        }
    }

    private class ViewerForeground extends JPanel implements Singleton {

        // private Scribe scribe = new Scribe("Tahoma", 11, Scribe.Style.BOLD);

        ViewerForeground() {
            new ViewerService().start();
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {

            // var glass = getGlassPane().getGraphics();
            // glass.setColor(Color.WHITE);
            // glass.fillRect(8, 8, 1300, 600);

            var g = (Graphics2D) graphics;
            Views.applyCoolness(g);

            g.setColor(View.outlineFabric.toColor());

            // corner_x0 = 13;

            int dx = corner_x0;

            // for (int i = 0; i < Viewer.this.stack.size(); i++) {
            // g.fillRect(dx - 5, 8, 9, 9);
            // dx += 16;
            // }

            // if (leaf != null) {
            // for (View view : leaf) {
            // if (!thumbnails.containsKey(view)) {
            // // thumbnails.put(view, view.);
            // _updateThumbnail(view);
            // }
            // BufferedImage image = (BufferedImage) thumbnails.get(view);
            // g.drawImage(image, null, dx, corner_y0);
            // // g.drawImage(image, dx, corner_y, null);
            // dx += image.getWidth() + 8;
            // }
            // }

            // if (Viewer.this.hasView()) {
            // String s = Viewer.this.getCurrentView().getIdentity();
            // g.setColor(Color.GRAY);
            // g.setFont(scribe.getFont());
            // g.drawString(s, dx - 3, 3 + g.getFontMetrics().getHeight());
            // dx += g.getFontMetrics().stringWidth(s);
            // }

            // dx += 3;

            corner_x1 = dx;

            corner_y1 = corner_y0 + (int) thumbnailHeight + 8;

            g.setStroke(View.outlineStroke);
            // g.drawLine(0, corner_y1, corner_x1, corner_y1);
            // g.drawLine(corner_x1, 0, corner_x1, corner_y1);
            // g.drawLine(corner_x1, padding, getWidth(), padding);
            // g.drawLine(padding, corner_y1, padding, getHeight());

            g.drawLine(0, getHeight() - padding, getWidth(), getHeight() - padding);
            g.drawLine(getWidth() - padding, 0, getWidth() - padding, getHeight());

            // no corner
            g.drawLine(0, padding, getWidth(), padding);
            g.drawLine(padding, 0, padding, getHeight());
        }
    }

    private Viewer() {

        super("Jile Viewer");

        setLayout(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                back();
            }
        });

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                refreshForeground();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    back();

                } else if (e.getKeyCode() == KeyEvent.VK_F11) {
                    singleton.setFullscreen(!fullscreen);

                } else if (e.getKeyCode() == KeyEvent.VK_C) {
                    // copy view as an image into clipbload
                    Clipboard.singleton().copy(singleton.getCurrentView().getImage());

                } else if (e.getKeyCode() == KeyEvent.VK_F9) {
                    Views.toggleDarkMode();
                    singleton.resetBackground();
                    singleton.thumbnails.clear();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (hasView()) {
                    View view = getCurrentView();
                    panOrigin = e.getPoint();
                    panOrigin.translate(-view.getX(), -view.getY());
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (hasView()) {
                    View view = getCurrentView();
                    panCurrent = e.getPoint();
                    panCurrent.translate((int) -panOrigin.getX(), (int) -panOrigin.getY());
                    view.setLocation(panCurrent);
                    refresh();
                }
            }
        });

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (hasView() && e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                    View view = getCurrentView();
                    int sign = (int) -Math.signum(e.getPreciseWheelRotation());
                    double magnitude = Math.abs(e.getPreciseWheelRotation()) * e.getScrollAmount() / 100;
                    Point location = view.getLocation();
                    if (e.isShiftDown()) {
                        // horizontal
                        location.translate(sign * Math.max(1, (int) (magnitude * view.getWidth())), 0);
                    } else {
                        // vertical
                        location.translate(0, sign * Math.max(1, (int) (magnitude * view.getHeight())));
                    }
                    view.setLocation(location);
                    refresh();
                }
            }
        });
    }

    private ViewerForeground fg = null;

    private void refreshForeground() {
        if (fg == null) {
            fg = new ViewerForeground();
            getLayeredPane().add(fg);
            getLayeredPane().setLayer(fg, LAYER_FOREGROUND);
        }
        fg.setSize(getInnerWidth(), getInnerHeight());
        fg.repaint();
    }

    public void show(View view) {
        // show(view, true);
        // }

        // private void show(View view, boolean genuine) {
        // if (genuine)
        // show(SOMETHING, false);
        view.refresh();
        if (hasView())
            getLayeredPane().remove(getCurrentView().getComponent());
        push(view);
        getLayeredPane().add(view.getComponent());
        getLayeredPane().setLayer(view.getComponent(), LAYER_VIEWS);
        setVisible(true);
        reinitialize();
    }

    public void back() {
        if (hasView()) {
            getLayeredPane().remove(pop().getComponent());
            if (hasView()) {
                View view = getCurrentView();
                getLayeredPane().add(view.getComponent());
                getLayeredPane().setLayer(view.getComponent(), LAYER_VIEWS);
                refreshForeground();
                reinitialize();
            }
        }
        if (!hasView()) {
            dispose();
            if (!Codestack.isRunning())
                System.exit(0);
        }
    }

    private boolean HAS_NOT_TRIED_FULLSCREEN_MODE_YET = true;

    private void reinitialize() {
        refreshForeground();
        View view = getCurrentView();
        if (fullscreen) {
            center(view);
        } else {
            fit(view, 32);
            center();
            if (center(view) && HAS_NOT_TRIED_FULLSCREEN_MODE_YET) {
                HAS_NOT_TRIED_FULLSCREEN_MODE_YET = false;
                setFullscreen(true);
            }
        }
        resetBackground();
        refresh();
    }

    private void fit(View view, int padding) {
        int w = view.getWidth() + padding * 2;
        int h = view.getHeight() + padding * 2;
        setInnerSize(w, h);
        view.setLocation(padding, padding);
    }

    private boolean center(View view) {
        int x = (int) ((getInnerWidth() - view.getWidth()) / 2);
        int y = (int) ((getInnerHeight() - view.getHeight()) / 2);
        view.setLocation(x, y);
        return x < 16 || y < 32;
    }

    private void center() {
        int screenWidth = (int) Views.screenSize.getWidth();
        int screenHeight = (int) Views.screenSize.getHeight();
        setLocation((screenWidth - getWidth()) / 2, (screenHeight - getHeight()) / 2);
    }

    private boolean fullscreen = false;

    public void setFullscreen(boolean fullscreen) {
        if (this.fullscreen != fullscreen) {
            this.fullscreen = fullscreen;

            if (isDisplayable())
                dispose();

            if (fullscreen) {

                // become full-screen, undecorated, and translucent
                setUndecorated(true);
                try {
                    setOpacity(0.93f);
                } catch (UnsupportedOperationException e) {
                }
                setExtendedState(JFrame.MAXIMIZED_BOTH);

            } else {

                // become windowed, decorated, and centered; but opaque
                setOpacity(1);
                setUndecorated(false);
                setExtendedState(JFrame.NORMAL);
            }
            setVisible(true);
            reinitialize();
        }
    }

    public void refresh() {
        Viewer.singleton().repaint();
    }

    private Fabric backgroundFabric = new Fabric(0.93f);

    public void resetBackground() {
        setBackground(backgroundFabric.toColor());
        refresh();
    }

    private class ViewerService extends Service {

        private View.Element<?> element = null;

        private View hintView = null;

        private Integer oldHash = null;

        @Override
        public boolean serve() {

            Point mouse = MouseInfo.getPointerInfo().getLocation();

            if (hasView()) {

                View view = getCurrentView();

                mouse.translate(-getInnerX() - view.getX(), -getInnerY() - view.getY());

                // System.out.print(" " + mouse.getX() + ", " + mouse.getY());

                if (element != null) {

                    if (!element.getShape().contains(mouse)) {

                        element.setMouseInside(false);
                        element = null;

                        if (hintView != null) {
                            getLayeredPane().remove(hintView.getComponent());
                            hintView = null;
                        }

                        refresh();
                    }

                } else {

                    element = view.findElementAt(mouse);

                    if (element != null) {

                        element.setMouseInside(true);

                        Hint hint;

                        if (element instanceof Hintful) {
                            hint = ((Hintful) element).getHint();
                        } else if (element.getModel() instanceof Hintful) {
                            hint = ((Hintful) element.getModel()).getHint();
                        } else {
                            hint = null;
                        }

                        if (hint != null) {
                            Integer newHash = hint.getHash();
                            if (oldHash != newHash) {
                                oldHash = newHash;
                                hintView = hint.visualize();
                                getLayeredPane().add(hintView.getComponent());
                                getLayeredPane().setLayer(hintView.getComponent(), LAYER_HINTS);
                                // hintView.refresh();
                                hintView.moveNearElement(element);
                            }
                        }
                        refresh();
                    }
                }
            }
            return false;
        }
    }
}
