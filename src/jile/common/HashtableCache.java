package jile.common;

public class HashtableCache<Wrapper, Contents> implements Cache<Wrapper, Contents> {

    private final Node[] array;

    private final int SIZE = 256;

    @SuppressWarnings("unchecked")
    public HashtableCache() {
        array = (Node[]) new Object[SIZE];
    }

    private class Node {
        final int hash;
        final Wrapper wrapper;
        Contents contents;
        Node next;

        Node(int hash, Wrapper wrapper, Contents contents, Node next) {
            this.hash = hash;
            this.wrapper = wrapper;
            this.contents = contents;
            this.next = next;
        }
    }

    private static int hash(Object key) {
        int h = key.hashCode();
        return (key == null) ? 0 : h ^ (h >>> 24);
    }

    @Override
    public Wrapper get(Contents contents) {
        Node e = getNode(contents);
        return e == null ? null : e.wrapper;
    }

    private final Node getNode(Contents contents) {
        int hash = hash(contents);
        Node first = array[(SIZE - 1) & hash];
        if (first != null) {
            Wrapper k = first.wrapper;
            if (first.hash == hash && (k == contents || (contents != null && contents.equals(k)))) {
                return first;
            }
            Node node = first.next;
            if (node != null) {
                do {
                    if (node.hash == hash
                            && ((k = node.wrapper) == contents || (contents != null && contents.equals(k))))
                        return node;
                } while ((node = node.next) != null);
            }
        }
        return null;
    }

    @Override
    public void add(Contents contents, Wrapper wrapper) throws CacheCollisionException {
        int hash = hash(wrapper);
        int i = (SIZE - 1) & hash;
        Node node = array[i];
        if (node == null)
            array[i] = new Node(hash, wrapper, contents, null);
        else {
            Node e;
            Wrapper k = node.wrapper;
            if (node.hash == hash && (k == wrapper || (wrapper != null && wrapper.equals(k))))
                e = node;
            else {
                while (true) {
                    e = node.next;
                    if (e == null) {
                        node.next = new Node(hash, wrapper, contents, null);
                        break;
                    }
                    k = e.wrapper;
                    if (e.hash == hash && (k == wrapper || (wrapper != null && wrapper.equals(k))))
                        break;
                    node = e;
                }
            }
            if (e != null) { // existing mapping for key
                // V oldValue = e.value;
                e.contents = contents; // only if absent = false TODO
                // return oldValue; REPLACED
            }
        }
    }
}
