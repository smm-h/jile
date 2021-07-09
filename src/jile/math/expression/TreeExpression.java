package jile.math.expression;

import java.util.LinkedList;
import java.util.List;

import jile.math.operations.Operation;
import jile.common.*;

public class TreeExpression implements Expression {

    // @SuppressWarnings("unchecked")
    // private final Object or = (Object)
    // FatOr.singleton();

    private final TraversibleTree<Object> tree;

    public TreeExpression() {
        tree = new LinkedTree<Object>();
    }

    public Object evaluate() {
        return evaluate(tree.getRoot());
    }

    public Object evaluate(Object node) {

        // node is either an operation or a value
        tree.goTo(node);

        // if the node is an operation
        if (node instanceof Operation) {

            // get it
            Operation operation = (Operation) node;

            // make a list of args to pass it
            List<Object> args = new LinkedList<Object>();

            // populate args with the children of node
            for (Object child : tree.getChildren(node))
                args.add(evaluate(child));

            // return the result of operating on those args
            return operation.operateUnchecked(Convert.List_to_Array(args));

        }

        // if the node is a value
        else {

            // return it
            return node;
        }
    }
}
