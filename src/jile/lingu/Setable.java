package jile.lingu;

import jile.common.LinkedTree;

public class Setable<T> {

    private static LinkedTree<String> tree;

    public static LinkedTree<String> tree() {
        if (tree == null) {
            tree = new LinkedTree<String>();
        }
        return tree;
    }

    /** dot separated, case-insensitive */
    private final String address, link;

    private T value = null;

    public Setable(Object owner, String address) {
        this(owner, address, null);
    }

    public Setable(Object owner, String address, T defaultValue) {
        this.link = owner.getClass().getCanonicalName(); // + owner.hashCode();
        this.address = address;
        this.value = defaultValue;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
