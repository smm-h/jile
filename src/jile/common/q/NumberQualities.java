package jile.common.q;

import static jile.common.q.Quality.*;

public interface NumberQualities {

    public static final Quality<Number> whole = new Quality<Number>() {
        @Override
        public boolean holdsFor(Number n) {
            return n.doubleValue() == n.intValue();
        }
    };

    public static final Quality<Number> even = new Quality<Number>() {
        @Override
        public boolean holdsFor(Number n) {
            return is(n, whole) && n.intValue() % 2 == 0;
        }
    };

    public static final Quality<Number> odd = new Quality<Number>() {
        @Override
        /**
         * if n is odd, n % 2 may be 1 or -1.
         * 
         * @see https://en.wikipedia.org/wiki/Modulo_operation#Common_pitfalls
         */
        public boolean holdsFor(Number n) {
            return is(n, whole) && n.intValue() % 2 != 0;
        }
    };

    public static final Quality<Number> prime = new Quality<Number>() {
        @Override
        public boolean holdsFor(Number n) {
            if (is(n, whole)) {
                // TODO
                return true;
            } else {
                return false;
            }
        }
    };

    public static class Divisible implements Quality<Number> {

        private final int d;

        private Divisible(int d) {
            this.d = d;
        }

        public static Divisible by(int d) {
            return new Divisible(d);
        }

        @Override
        public boolean holdsFor(Number n) {
            return is(n, whole) && n.intValue() % d == 0;
        }

    }

    public static interface Multiple {
        public static Divisible of(int d) {
            return Divisible.by(d);
        }
    }

    public static void main(String[] args) {

        int x = 30;

        System.out.println(is(x, even));
        System.out.println(is(x, odd));
        System.out.println(is(x, prime));
        System.out.println(is(x, Divisible.by(3)));
        System.out.println(is(x, Divisible.by(4)));
        System.out.println(is(x, Multiple.of(5)));

        var both_even_and_odd = and(even, odd);
        var neither_even_nor_odd = or(even, odd);

        System.out.println(is(2, both_even_and_odd));
        System.out.println(is(2, neither_even_nor_odd));
        System.out.println(is(2.0, neither_even_nor_odd));
        System.out.println(is(2.1, neither_even_nor_odd));
    }

}
