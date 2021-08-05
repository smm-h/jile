package jile.vis;

import java.awt.FontMetrics;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

import jile.lingu.Code;
import jile.lingu.Port;
import jile.lingu.Codestack;
import jile.lingu.Setable;
import jile.common.Convertor;
import jile.common.LinkedTree;
import jile.common.Tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// @SuppressWarnings("serial")
public class TreeView<T> extends View {

    public static void main(String[] args) {
        Codestack.makeAndShow("tlg/test.tlg");
    }

    private int depth = 0;
    private final LinkedTree<NodeView> tree;
    private NodeView drawingRoot, selectedNode;

    private boolean initialized = false;

    public final Setable<Fabric> fabric_edge = new Setable<Fabric>(this, "connector.fabric", new Fabric(0.4f));
    public final Setable<Fabric> fabric_border = new Setable<Fabric>(this, "border.fabric", new Fabric(0.4f));
    public final Setable<Fabric> fabric_text = new Setable<Fabric>(this, "text.fabric", new Fabric(0.4f));
    public final Setable<Fabric> fabric_node = new Setable<Fabric>(this, "node.fabric", new Fabric(0.9f));

    public final Setable<Views.Connector> connectorType = new Setable<Views.Connector>(this, "connector.type",
            Views.Connector.SERIOUS_CURVE);

    public final Setable<Float> padding = new Setable<Float>(this, "padding", 12f);
    public final Setable<Float> border_width = new Setable<Float>(this, "border.width", 2f);
    public final Setable<Float> edge_widgh = new Setable<Float>(this, "connector.width", 2f);

    /** set in {@link #setScribe} */
    public final Setable<Float> node_padding = new Setable<Float>(this, "node.padding", 6f);
    public final Setable<Float> node_spacing_v = new Setable<Float>(this, "node.spacing.vertical", 48f);
    public final Setable<Float> node_spacing_h = new Setable<Float>(this, "node.spacing.horizontal", 10f);

    private float lineHeight;

    private NodeView nodeBeingDragged;
    private float draggingX, draggingY;

    @Override
    public String getIdentity() {
        return "Tree View" + (sourceCode == null ? "" : " of " + sourceCode.getIdentity());
    }

    public TreeView(View.Mode mode) {
        this(mode, null, null);
    }

    public TreeView(View.Mode mode, Code sourceCode) {
        this(mode, null, sourceCode);
    }

    public TreeView(View.Mode mode, LinkedTree<T> src) {
        this(mode, src, null);
    }

    private final Code sourceCode;

    public TreeView(View.Mode mode, LinkedTree<T> src, Code sourceCode) {

        super(mode);

        initialized = true;

        backgroundFabric = new Fabric(0, 0);

        if (src == null)
            tree = new LinkedTree<NodeView>();
        else {
            tree = new LinkedTree<NodeView>(src, new Convertor<T, TreeView<T>.NodeView>() {

                @Override
                public TreeView<T>.NodeView convert(T source) {
                    return new NodeView(source);
                }
            });
        }

        this.sourceCode = sourceCode;

        setScribe(new Scribe("Segoe UI", 18));

        getComponent().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton()) {
                    case 1:
                        var found = drawingRoot.findByPosition(e.getX(), e.getY());
                        if (found != null) {
                            setSelected(found);
                        }
                        break;
                    case 3:
                        setSelected(null);
                        break;
                }
            }
        });

        getComponent().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var found = drawingRoot.findByPosition(e.getX(), e.getY());
                if (found != null) {
                    nodeBeingDragged = found;
                    draggingX = e.getX() - nodeBeingDragged.x;
                    draggingY = e.getY() - nodeBeingDragged.y;
                }
            }
        });

        getComponent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                nodeBeingDragged = null;
            }
        });

        getComponent().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (nodeBeingDragged != null) {
                    nodeBeingDragged.x = e.getX() - draggingX;
                    if (!e.isControlDown())
                        nodeBeingDragged.y = e.getY() - draggingY;
                    nodeBeingDragged.remakeShape();
                    // Viewer.refresh();
                    beTainted();
                }
            }
        });
    }

    @Override
    public void setScribe(Scribe scribe) {
        super.setScribe(scribe);
        // TODO 65
        lineHeight = getScribe().getMetrics().getHeight() * 65 / 100;
        if (initialized) {
            node_padding.set(lineHeight / 2); // * 35 / 100;
            node_spacing_v.set(lineHeight + node_padding.get() + border_width.get() * 2 + lineHeight * 3);
            node_spacing_h.set(lineHeight / 4);
        }
    }

    public void setSelected(NodeView selected) {
        if (this.selectedNode != selected) {
            this.selectedNode = selected;
            beTainted();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        // graphics.setColor(new Color(255, 222, 100));
        // graphics.fillRect(0, 0, getWidth(), getHeight());
        // if (tree != null) {
        // forceRefresh();
        // g.setFont(getFont());
        drawingRoot.draw(g);
        // if (selectedNode != null) selectedNode.draw(g);
        // } else {
        // System.out.println("tree is null");
        // }
    }

    @Override
    public void _refresh() {

        if (drawingRoot == null)
            drawingRoot = tree.getRoot();

        drawingRoot.refreshDimensions(getScribe().getMetrics(), 0);

        float e = border_width.get() + padding.get();

        if (nodeBeingDragged == null) {
            float x0 = e + drawingRoot.biggerWidth / 2;
            float y0 = e + drawingRoot.height / 2;
            drawingRoot.refreshPosition(x0, y0);
        }

        int w = (int) (e * 2 + drawingRoot.biggerWidth);
        int h = (int) (e * 2 + drawingRoot.height + depth * node_spacing_v.get());
        setSize(w, h);
    }

    public Convertor<T, String> toText = new Convertor<T, String>() {

        @Override
        public String convert(T source) {
            return source.toString();
        }
    };

    private class NodeView extends View.Element<T> {
        private T model;
        private Shape shape;
        private String text = "(empty)";
        private float x, y, width, biggerWidth, childrenWidth, height;

        private boolean visible = true;

        public NodeView(T data) {
            this.model = data;
        }

        @Override
        public T getModel() {
            return model;
        }

        public void setVisibility(boolean visible) {
            if (this.visible != visible) {
                this.visible = visible;
                beTainted();
            }
        }

        @Override
        public String toString() {
            return toText.convert(model);
        }

        @SuppressWarnings("unused")
        void draw_(Graphics2D graphics) {
            if (visible) {

                // draw my node-ness
                graphics.setStroke(new BasicStroke(border_width.get()));

                Dye dye = selectedNode == this ? Views.accent : null;

                graphics.setColor(fabric_node.get().toColor(dye));
                graphics.fill(shape);

                graphics.setColor(fabric_border.get().toColor(dye));
                graphics.draw(shape);

                if (tree.hasChildren(this)) {

                    // draw the edges from me to my children
                    graphics.setStroke(new BasicStroke(edge_widgh.get()));

                    float e = border_width.get(); // + edge_widgh.get() / 2;
                    float y1 = y + height / 2 + e;

                    // no one is selected
                    if (selectedNode == null) {
                        graphics.setColor(fabric_edge.get().toColor());
                        for (NodeView child : tree.getChildren(this)) {
                            if (child.visible) {
                                graphics.draw(Views.makeVerticalConnector(x, y1, child.x,
                                        child.y - child.height / 2 - e, connectorType.get()));
                            }
                        }
                    } else
                    // I am selected
                    if (selectedNode == this) {
                        for (NodeView child : tree.getChildren(this)) {
                            if (child.visible) {
                                graphics.setPaint(
                                        Views.makeGradientPaint(x, y1, child.x, child.y - child.height / 2 - e,
                                                fabric_edge.get().toColor(dye), fabric_edge.get().toColor()));
                                graphics.draw(Views.makeVerticalConnector(x, y1, child.x,
                                        child.y - child.height / 2 - e, connectorType.get()));
                            }
                        }
                    }
                    // A descendent of mine may be selected
                    else {
                        for (NodeView child : tree.getChildren(this)) {
                            if (child.visible) {
                                if (selectedNode == child) {
                                    graphics.setPaint(Views.makeGradientPaint(x, y1, child.x,
                                            child.y - child.height / 2 - e, fabric_edge.get().toColor(),
                                            fabric_edge.get().toColor(Views.accent)));
                                } else {
                                    graphics.setColor(fabric_edge.get().toColor());
                                }
                                graphics.draw(Views.makeVerticalConnector(x, y1, child.x,
                                        child.y - child.height / 2 - e, connectorType.get()));
                            }
                        }
                    }
                }

                float e2 = node_padding.get() + border_width.get();

                // draw my text
                graphics.setColor(fabric_text.get().toColor(dye));
                graphics.drawString(text, x - width / 2 + e2, y + height / 2 - e2);

                // draw my children
                for (NodeView node : tree.getChildren(this)) {
                    node.draw_(graphics);
                }
            }
        }

        // cs = me == selected
        // for child in children {cs = cs || draw(child)}
        // draw connector to my parent
        // draw my face
        // draw my text
        // return cs

        // @SuppressWarnings("unused")
        boolean draw(Graphics2D graphics) {
            // graphics.setFont(getFont()); TODO should this remain commented?
            boolean descendentIsSelected = false;
            if (visible) {

                descendentIsSelected = this == selectedNode;

                // draw my children
                if (tree.hasChildren(this)) {
                    for (NodeView node : tree.getChildren(this)) {
                        descendentIsSelected |= node.draw(graphics);
                    }
                }

                var parent = tree.getParent(this);

                if (parent != null) {

                    float e = border_width.get(); // + edge_widgh.get() / 2;

                    float x1 = parent.x;
                    float y1 = parent.y + parent.height / 2 + e;
                    float x2 = x;
                    float y2 = y - height / 2 - e;

                    var connector = Views.makeVerticalConnector(x1, y1, x2, y2, connectorType.get());

                    var c1 = fabric_edge.get().toColor(Views.accent);
                    var c2 = fabric_edge.get().toColor();

                    if (descendentIsSelected) {
                        graphics.setColor(c1);
                    } else if (parent == selectedNode) {
                        graphics.setPaint(Views.makeGradientPaint(x1, y1, x2, y2, c1, c2));
                    } else {
                        graphics.setColor(c2);
                    }

                    // TODO turn `new BasicStroke`s in `draw*` methods into field accesses
                    graphics.setStroke(new BasicStroke(edge_widgh.get() * (descendentIsSelected ? 4 : 1)));
                    graphics.draw(connector);
                }

                // draw my node-ness
                graphics.setStroke(new BasicStroke(border_width.get()));

                Dye dye;

                dye = selectedNode == this || isMouseInside() ? Views.accent : null;

                graphics.setColor(fabric_node.get().toColor(dye));
                graphics.fill(shape);

                dye = descendentIsSelected || isMouseInside() ? Views.accent : null;

                graphics.setColor(fabric_border.get().toColor(dye));
                graphics.draw(shape);

                float e2 = node_padding.get() + border_width.get();

                // draw my text
                graphics.setColor(fabric_text.get().toColor(dye));
                graphics.drawString(text, x - width / 2 + e2, y + height / 2 - e2);

            }
            return descendentIsSelected;
        }

        private void refreshDimensions(FontMetrics f, int d) {
            if (visible) {
                depth = Math.max(depth, d);
                text = toString();
                int n = 1;
                for (Character c : text.toCharArray())
                    if (c.equals('\n'))
                        n++;
                // float lineHeight = f.getHeight() * 65 / 100;
                // float lineHeight = f.getAscent() - f.getDescent();
                // float lineHeight = f.getHeight() - f.getLeading();

                float e = (node_padding.get() + border_width.get()) * 2;
                height = lineHeight * n + e;
                width = f.stringWidth(text) + e;
                childrenWidth = -node_spacing_h.get() - border_width.get();

                for (NodeView child : tree.getChildren(this)) {
                    child.refreshDimensions(f, d + 1);
                    if (child.visible) {
                        childrenWidth += child.biggerWidth + node_spacing_h.get() + border_width.get();
                    }
                }
                biggerWidth = Math.max(width, childrenWidth);
            }
        }

        private void remakeShape() {
            float arc = Math.min(width, height) / 2;
            shape = new RoundRectangle2D.Float((float) (x - width / 2), (float) (y - height / 2), (float) width,
                    (float) height, arc, arc);
        }

        private void refreshPosition(float x0, float y0) {
            if (visible) {
                x = x0;
                y = y0;

                float x2 = x0 - childrenWidth / 2;

                float y2 = y0 + node_spacing_v.get();

                float sx = 0;
                int cx = 0;

                for (NodeView child : tree.getChildren(this)) {
                    if (child.visible) {
                        child.refreshPosition(x2 + child.biggerWidth / 2, y2);
                        x2 += child.biggerWidth + node_spacing_h.get() + border_width.get();

                        sx += child.x;
                        cx++;
                    }
                }

                if (cx != 0)
                    x = sx / cx;

                // NodeView first = tree.getFirstChild(this), last = tree.getLastChild(this);
                // if (last != null)
                // x = (first.x + last.x) / 2;
                // else if (first != null)
                // x = first.x;

                // NodeView chosenChild = tree.getMiddleChild(this);
                // if (chosenChild != null)
                // x = chosenChild.x;

                remakeShape();
            }
        }

        public NodeView findByPosition(Point point) {
            return findByPosition((float) point.getX(), (float) point.getY());
        }

        public NodeView findByPosition(float px, float py) {
            NodeView found = null;
            if (shape.contains(px, py)) {
                found = this;
            } else {
                for (NodeView child : tree.getChildren(this)) {
                    if (child.visible) {
                        found = child.findByPosition(px, py);
                        if (found != null)
                            break;
                    }
                }
            }
            return found;
        }

        @Override
        public Shape getShape() {
            return shape;
        }
    }

    // public void setAttribute(String name, float value) throws
    // NoSuchFieldException {
    // switch (name) {
    // case "vsep":
    // node_spacing_v.set(value);
    // break;
    // case "hsep":
    // node_spacing_h.set(value);
    // break;
    // case "padding":
    // node_padding.set(value);
    // break;
    // case "border":
    // border_width.set(value);
    // break;
    // default:
    // throw new NoSuchFieldException(name);
    // }
    // }

    public void setSelectedNodeVisibility(boolean b) {
        selectedNode.setVisibility(b);
    }

    public void viewSelectedNodeAsRoot() {
        drawingRoot = selectedNode;
    }

    public void addAndGoTo(T data) {
        tree.addAndGoTo(new NodeView(data));
    }

    public void goBack() {
        tree.goBack();
    }

    public void goToLastAdded() {
        tree.goToLastAdded();
    }

    public void add(T data) {
        tree.add(new NodeView(data));
    }

    public void addAndSelect(T data) {
        var node = new NodeView(data);
        tree.add(node);
        selectedNode = node;
    }

    @Override
    public String toString() {
        return tree.toString();
    }

    // @Override
    // public Code getSourceCode() {
    // return sourceCode;
    // }

    // @Override
    // public String getRepresenation() {
    // return tree.getRepresenation();
    // }

    @SuppressWarnings("unchecked") // TODO why does this warning generate
    @Override
    public Element<T> findElementAt(Point point) {
        return drawingRoot.findByPosition(point);
    }

    public static final Port<Tree<? extends Object>> port = new Port<Tree<? extends Object>>("TreeView:port");
    // public static final Port<Tree<? extends Object>> port = new Port<Tree<?
    // extends Object>>("TreeView:port");
}
