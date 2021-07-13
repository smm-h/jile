package jile.nilex;

public interface Processor extends Linter {

    /** Never call this directly. */
    public void _process(Code code);

    public default void process(Code code) {
        if (code.canBeProcessed())
            _process(code);
    }

    public boolean canBeExtended();

    public void extend(Processor processor) throws UnsupportedOperationException;

    public int getSize();
}
