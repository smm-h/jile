package jile.common;

import java.util.*;
import java.util.function.Predicate;

import jile.lingu.Code;
import jile.vis.TreeView;
import jile.vis.View.Mode;

public class LinkedTree<T> implements MutableTree<T> {

    private Node pointer, root;
    private final Map<T, Node> nodes;

    public LinkedTree() {
        this((Code) null);
    }

    private final Code sourceCode;

    public LinkedTree(Code sourceCode) {
        this.sourceCode = sourceCode;
        this.nodes = new HashMap<T, Node>();
    }

    @Override
    public Code getSourceCode() {
        return sourceCode;
    }

    public LinkedTree(LinkedTree<T> src) {
        this(src.getSourceCode());
        addFrom(Objects.requireNonNull(src.root), new Copier.NullCopier<T>());
    }

    public LinkedTree(LinkedTree<T> src, Copier<T> copier) {
        this(src.getSourceCode());
        addFrom(Objects.requireNonNull(src.root), copier);
    }

    public <T2> LinkedTree(LinkedTree<T2> src, Convertor<T2, T> converter) {
        this(src.getSourceCode());
        addFrom(Objects.requireNonNull(src.root), converter);
    }

    @Override
    public <T2> LinkedTree<T2> convert(Convertor<T, T2> convertor) {

        var converted = new LinkedTree<T2>();

        converted.addFrom(Objects.requireNonNull(root), convertor);

        return converted;
    }

    @Override
    public <T2> LinkedTree<T2> cast() {

        var cast = new LinkedTree<T2>();

        cast.addFrom(Objects.requireNonNull(root));

        return cast;
    }

    private <T0> void addFrom(LinkedTree<T0>.Node node, Convertor<T0, T> convertor) {

        addAndGoTo(convertor.convert(node.data));

        if (node.children != null)
            for (var child : node.children)
                addFrom(child, convertor);

        goBack();
    }

    private void addFrom(Node node, Copier<T> copier) {

        addAndGoTo(copier.copy(node.data));

        if (node.children != null)
            for (var child : node.children)
                addFrom(child, copier);

        goBack();
    }

    @SuppressWarnings("unchecked")
    private <T2> void addFrom(LinkedTree<T2>.Node node) {

        addAndGoTo((T) node.data);

        if (node.children != null)
            for (var child : node.children)
                addFrom(child);

        goBack();
    }

    @Override
    public String toString() {
        return getRepresenation();
    }

    @Override
    public TreeView<T> visualize() {
        // TreeView<T> view = new TreeView<T>();
        // Objects.requireNonNull(root).visualize(view);
        // return view;
        return new TreeView<T>(Mode.INTERACTIVE, this, sourceCode);
    }

    private Node findByData(T data) {
        return Objects.requireNonNull(nodes.get(data));
    }

    @Override
    public boolean contains(T data) {
        return nodes.containsKey(data);
    }

    @Override
    public void goTo(T data) {
        goTo(findByData(data));
    }

    @Override
    public T getPointer() {
        if (pointer == null)
            return null;
        else
            return pointer.data;
    }

    private void goTo(Node node) {
        pointer = Objects.requireNonNull(node);
    }

    @Override
    public void goToLastAdded() {
        pointer = pointer == null ? root : pointer.children.getLast();
    }

    @Override
    public void goToChild(int childIndex) throws IndexOutOfBoundsException {
        pointer = pointer.children.get(childIndex);
    }

    @Override
    public void goToRoot() {
        pointer = Objects.requireNonNull(root);
    }

    @Override
    public void goBack() {
        if (pointer != null) {
            pointer = pointer.parent;
        } else if (root != null) {
            pointer = root;
            System.out.println("goBack() restarted from root");
        } else {
            System.out.println("goBack() failed: pointer is null");
        }
    }

    @Override
    public void addAndGoTo(T data) {
        goTo(addAndReturn(data));
    }

    @Override
    public void add(T data) {
        addAndReturn(data);
    }

    private Node addAndReturn(T data) {
        Node node = new Node(data, pointer);
        nodes.put(data, node);
        if (node.parent == null) {
            if (root == null) {
                root = node;
            } else {
                System.out.println("A tree cannot have more than one roots");
            }
        } else {
            if (node.parent.children == null) {
                node.parent.children = new LinkedList<Node>();
            }
            node.parent.children.add(node);
        }
        return node;
    }

    @Override
    public T remove() {

        // which node are we removing?
        Node node = pointer;

        // what is its data?
        T key = node.data;

        // let us update the pointer to its parent
        pointer = node.parent;

        // if we just removed the root,
        if (pointer == null) {

            // let it be known
            root = null;
        }

        // otherwise
        else {

            // and remove it from its parent's children
            pointer.children.remove(node);

            // let's not keep empty children lists
            if (pointer.children.isEmpty())
                pointer.children = null;
        }

        // and finally, return the value of the removed node
        return key;
    }

    public Convertor<T, String> toText = new Convertor<T, String>() {

        @Override
        public String convert(T source) {
            return source.toString();
        }
    };

    private class Node {
        final T data;
        Node parent;
        LinkedList<Node> children = null;

        Node(T data, Node parent) {
            this.data = data;
            this.parent = parent;
        }

        String getRepresenation(int depth) {
            String represenation = "\t".repeat(depth) + "\"" + toText.convert(data) + "\"";
            if (children != null) {
                represenation += " {\n";
                for (Node child : children) {
                    represenation += child.getRepresenation(depth + 1);
                }
                represenation += "\t".repeat(depth) + "}";
            }
            represenation += "\n";
            // }
            return represenation;
        }

        // private void visualize(TreeView<T> view) {
        // view.addAndGoTo(data);
        // if (children != null) {
        // for (Node child : children) {
        // child.visualize(view);
        // }
        // }
        // view.goBack();
        // }

        void filterNode(Collection<T> addable, Predicate<Node> predicate) {
            if (predicate.test(this))
                addable.add(data);
            if (children != null) {
                for (Node child : children) {
                    child.filterNode(addable, predicate);
                }
            }
        }

        void filterValue(Collection<T> addable, Predicate<T> predicate) {
            if (predicate.test(data))
                addable.add(data);
            if (children != null) {
                for (Node child : children) {
                    child.filterValue(addable, predicate);
                }
            }
        }

        public boolean hasChildren() {
            return children == null ? false : !children.isEmpty();
        }
    }

    @Override
    public String getRepresenation() {
        return root.getRepresenation(0);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public T getRoot() {
        if (root == null)
            return null;
        else
            return root.data;
    }

    public T getFirstChild(T data) {
        var f = findByData(data);
        if (f.children == null || f.children.isEmpty())
            return null;
        else
            return f.children.get(0).data;
    }

    public T getLastChild(T data) {
        var f = findByData(data);
        if (f.children == null || f.children.isEmpty())
            return null;
        else
            return f.children.get(f.children.size() - 1).data;
    }

    public T getMiddleChild(T data) {
        var f = findByData(data);
        if (f.children == null || f.children.isEmpty())
            return null;
        else
            return f.children.get((f.children.size() - 1) / 2).data;
    }

    @Override
    public T getParent(T data) {
        var f = findByData(data);
        if (f.parent == null)
            return null;
        else
            return f.parent.data;
    }

    private class ShallowIterable implements Iterable<T> {
        private final Node node;

        public ShallowIterable(Node node) {
            this.node = node;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private int index = 0;

                @Override
                public boolean hasNext() {
                    if (node.children != null)
                        return index != node.children.size();
                    else
                        return false;
                }

                @Override
                public T next() {
                    return node.children.get(index++).data;
                }
            };
        }
    }

    @Override
    public Iterable<T> getChildren(T data) {
        return new ShallowIterable(findByData(data));
    }

    @Override
    public List<T> getLeaf() {
        return filterNodes(new LinkedList<T>(), new Predicate<Node>() {
            @Override
            public boolean test(LinkedTree<T>.Node node) {
                return !node.hasChildren();
            }
        });
    }

    private <C extends Collection<T>> C filterNodes(C collection, Predicate<Node> predicate) {
        if (root != null)
            root.filterNode(collection, predicate);
        return collection;
    }

    public <C extends Collection<T>> C filter(C collection, Predicate<T> predicate) {
        if (root != null)
            root.filterValue(collection, predicate);
        return collection;
    }

    @Override
    public boolean hasChildren(T data) {
        var children = findByData(data).children;
        return children == null ? false : !children.isEmpty();
    }

    public Iterator<T> depthFirstIterator() {
        return depthFirstIterator(root);
    }

    public Iterator<T> depthFirstIterator(T from) {
        return depthFirstIterator(findByData(from));
    }

    private Iterator<T> depthFirstIterator(Node iterationRoot) {
        return new Iterator<T>() {
            private Stack<Node> stack;

            @Override
            public boolean hasNext() {
                if (stack == null) {
                    stack = new Stack<Node>();
                    stack.push(iterationRoot);
                }
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                Node next = stack.pop();

                if (next.children != null)
                    for (Node child : Common.over(next.children.descendingIterator()))
                        stack.push(child);

                return next.data;
            }
        };
    }

    public Iterator<T> breadthFirstIterator() {
        return breadthFirstIterator(root);
    }

    public Iterator<T> breadthFirstIterator(T from) {
        return breadthFirstIterator(findByData(from));
    }

    private Iterator<T> breadthFirstIterator(Node iterationRoot) {
        return new Iterator<T>() {
            private Stack<Node> stack;

            @Override
            public boolean hasNext() {
                if (stack == null) {
                    stack = new Stack<Node>();
                    stack.push(iterationRoot);
                }
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                Node next = stack.pop();

                if (next.children != null)
                    for (Node child : Common.over(next.children.descendingIterator()))
                        stack.push(child);

                return next.data;
            }
        };
    }
}