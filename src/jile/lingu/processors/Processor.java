package jile.lingu.processors;

import jile.lingu.Code;
import jile.lingu.Linter;

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
