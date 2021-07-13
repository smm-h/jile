package jile.math.operations;

import jile.math.exceptions.ImpreciseOperationException;
import jile.math.numbers.Integer;

public class IntegerModulo implements Modulo<Integer> {

    @Override
    public Integer operate(Integer a, Integer b) {
        if (a.isPrecise() && b.isPrecise()) {
            return Integer.fromContents((long) (a.approximate() % b.approximate()));
        } else {
            throw new ImpreciseOperationException();
        }
    }
}
