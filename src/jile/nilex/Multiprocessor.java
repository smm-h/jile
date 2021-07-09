package jile.nilex;

import java.util.*;

public class Multiprocessor implements Processor {

    private final List<Processor> processors = new LinkedList<Processor>();

    private boolean sealed = false;

    public Multiprocessor(Processor... processors) {
        for (int i = 0; i < processors.length; i++) {
            extend(processors[i]);
        }
    }

    /** abstract classes must not call this so they can be extended */
    public void seal() {
        sealed = true;
    }

    @Override
    public final void _process(Code code) {
        for (Processor processor : processors)
            processor.process(code);
    }

    @Override
    public boolean canBeExtended() {
        return !sealed;
    }

    @Override
    public void extend(Processor processor) {
        if (canBeExtended()) {
            processors.add(processor);
            // System.out.println(processor.getClass().getName());
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public int getSize() {
        return processors.size();
    }
}