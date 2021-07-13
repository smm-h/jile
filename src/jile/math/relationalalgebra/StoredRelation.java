package jile.math.relationalalgebra;

import jile.math.settheory.*;
import jile.math.tuples.*;

/**
 * A {@link StoredRelation} is a {@link Set} of {@link FiniteTuple}s.
 * 
 * @see https://en.wikipedia.org/wiki/Relation_(database)
 */

public class StoredRelation extends StoredSet<FiniteTuple> {

    // private final TupleHeader header;

    public StoredRelation(Iterable<FiniteTuple> tuples) {
        super(tuples);
    }
}
