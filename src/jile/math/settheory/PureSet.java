package jile.math.settheory;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import jile.vis.Viewer;

/**
 * https://en.wikipedia.org/wiki/Von_Neumann_universe
 */

public class PureSet extends StoredSet<PureSet> {

    private PureSet(List<PureSet> elements) {
        super(elements);
    }

    public static PureSet fromOrdinal(int ordinal) {
        if (ordinal == 0) {
            return new PureSet(null);
        } else {
            List<PureSet> list = new LinkedList<PureSet>();
            for (int i = 0; i < ordinal; i++) {
                list.add(fromOrdinal(i));
            }
            return new PureSet(list);
        }
    }

    public static PureSet fromString(String code) {
        Stack<List<PureSet>> stack = new Stack<List<PureSet>>();
        stack.push(new LinkedList<PureSet>());
        for (char c : code.toCharArray()) {
            switch (c) {
            case '{':
                stack.push(new LinkedList<PureSet>());
                break;
            case '}':
                PureSet p = new PureSet(stack.pop());
                stack.peek().add(p);
                break;
            }
        }
        return new PureSet(stack.pop());
    }

    @Override
    public String toString() {
        String string = "{";
        if (getCardinality() > 0)
            for (PureSet p : this)
                string += p.toString();
        return string + "}";
    }

    public static void main(String[] args) {
        // PureSet p = PureSet.fromString("{}{{}{}}");
        PureSet p = PureSet.fromOrdinal(7);
        // System.out.println(p);
        Viewer.singleton().show(p.toTree().visualize());
    }

}
