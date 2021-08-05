package jile.nilex.processors;

import jile.nilex.Code;
import jile.nilex.Linter;

public interface Processor extends Linter {

    /**
     * Only ever call this inside another {@link Processor#process} overriding or
     * the {@link Code#beProcessed}.
     */
    public void process(Code code);

    public boolean canBeExtended();

    public void extend(Processor processor) throws UnsupportedOperationException;

    public int getSize();
}
