package jile.math.sequence;

import jile.math.settheory.OrderedSet;

/**
 * A {@link Sequence} is an {@link OrderedSet}.
 */
public interface Sequence extends OrderedSet {

    public Object getElementAt(int index);

    public int chooseElement();

    @Override
    default Object choose() {
        return getElementAt(chooseElement());
    }

}
