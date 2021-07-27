package jile.math;

import jile.common.*;
import jile.math.settheory.Set;

public interface Convert {

    public static Tree<Object> Set_to_Tree(Set set) {
        LinkedTree<Object> tree = new LinkedTree<Object>();
        Set_to_Tree(set, tree);
        return tree;
    }

    private static void Set_to_Tree(Set set, MutableTree<Object> tree) {
        tree.addAndGoTo(set);
        for (Object object : set.excerpt()) {
            Object element = (Object) object;
            if (element instanceof Set) {
                Set_to_Tree(((Set) element), tree);
            } else {
                tree.add(element);
            }
        }
        tree.goBack();
    }
}
