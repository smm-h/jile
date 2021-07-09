package jile.cs;

import jile.math.tuples.Tuple;
import jile.vis.Visualizable;

public interface Solution<P extends Problem<I, O>, I extends Tuple, O extends Tuple> {

    public O solve(I i);

    public Visualizable getSolution(I i);
}
