package jile.nilex;

import jile.common.Singleton;

public class VirtualTokenType extends IndividualTokenType implements Singleton {

    private static VirtualTokenType singleton;

    public static VirtualTokenType singleton() {
        if (singleton == null) {
            singleton = new VirtualTokenType();
        }
        return singleton;
    }

    private VirtualTokenType() {
        super("<~>");
    }

    public final NullToken nullToken = new NullToken();

    /**
     * For when a non-null token must be returned.
     */
    public class NullToken extends IndividualToken implements Singleton {

        private NullToken() {
            super("", -1);
        }

        @Override
        public boolean is(Iterable<String> types) {
            return false;
        }

        @Override
        public boolean is(String type) {
            return false;
        }

        @Override
        public boolean is(String... types) {
            return false;
        }
    }
}