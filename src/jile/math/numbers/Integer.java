package jile.math.numbers;

import jile.math.operations.Addition;
import jile.math.operations.Multiplication;

/**
 * An {@link Integer} is any {@link Real} number that can be written without a
 * {@link Fraction}.
 */
public interface Integer extends Real {
    public static Integer fromContents(long contents) {
        return (Integer) Real.fromContents((double) contents);
    }

    public static Integer fromContents(int contents) {
        return (Integer) Real.fromContents((double) contents);
    }

    default public Integer add(int other) {
        return add(fromContents(other));
    }

    default public Integer multiply(int other) {
        return multiply(fromContents(other));
    }

    default public Integer add(Integer other) {
        return addition.operate(this, other);
    }

    default public Integer multiply(Integer other) {
        return multiplication.operate(this, other);
    }

    public static Addition<Integer> addition = new Addition<Integer>() {
        @Override
        public Integer operate(Integer a, Integer b) {
            return null;
        }
    };

    public static Multiplication<Integer> multiplication = new Multiplication<Integer>() {
        @Override
        public Integer operate(Integer a, Integer b) {
            return null;
        }
    };
}
