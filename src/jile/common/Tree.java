package jile.common;

import java.util.List;

import jile.lingu.Code;
import jile.lingu.Decodeable;
import jile.lingu.Encodeable;
import jile.vis.TreeView;
import jile.vis.Visualizable;

public interface Tree<T> extends Encodeable, Visualizable, Decodeable {

    @Override
    default public Code encode() {
        return new Code(Resource.of(getRepresenation(), "tlg"));
    }

    public boolean contains(T data);

    public boolean isEmpty();

    public T getRoot();

    public List<T> getLeaf();

    public T getParent(T data);

    public Iterable<T> getChildren(T data);

    public boolean hasChildren(T data);

    public String getRepresenation();

    @Override
    public TreeView<T> visualize();

    @SuppressWarnings("rawtypes")
    static final LinkedTree EMPTY_TREE = new LinkedTree<>();

    @SuppressWarnings("unchecked")
    public static <T> Tree<T> emptyTree() {
        return (LinkedTree<T>) EMPTY_TREE;
    }

    /**
     * To convert an n-ary tree to 2-ary, in every node, iterate over the siblings
     * from left to right, and make each one; but before that for each node
     * disassociate it from all its children children except the leftmost one.
     */
    // public void toBinary(); TODO
}
