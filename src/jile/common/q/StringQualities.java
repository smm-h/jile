package jile.common.q;

import static jile.common.q.Quality.*;

import jile.common.Common;

public interface StringQualities {

    public static final Quality<String> empty = new Quality<String>() {
        @Override
        public boolean holdsFor(String s) {
            return s.isEmpty();
        }
    };

    public static final Quality<String> blank = new Quality<String>() {
        @Override
        public boolean holdsFor(String s) {
            return s.isBlank();
        }
    };

    public static final Quality<String> palindrome = new Quality<String>() {
        @Override
        public boolean holdsFor(String s) {
            if (is(s, empty))
                return true;
            int n = s.length() - 1;
            int h = Common.ceil(n / 2.0);
            for (int i = 0; i < h; i++)
                if (s.charAt(i) != s.charAt(n - i))
                    return false;
            return true;
        }
    };

    public static void main(String[] args) {
        System.out.println(is("abc", palindrome));
        System.out.println(is("abcba", palindrome));
        System.out.println(is("abba", palindrome));
        System.out.println(is("", palindrome));
        System.out.println(is("x", palindrome));
        System.out.println(is("xx", palindrome));
        System.out.println(is("xxx", palindrome));
    }

}
