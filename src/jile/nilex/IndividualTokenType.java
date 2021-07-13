package jile.nilex;

import java.util.Objects;

import jile.vis.Scribe;

abstract public class IndividualTokenType implements TokenType {
    public final String title;
    private Scribe scribe;

    public IndividualTokenType(String title) {
        this.title = title;
    }

    public Scribe getScribe() {
        return scribe;
    }

    public void setScribe(Scribe scribe) {
        this.scribe = scribe;
    }

    @Override
    public final String toString() {
        return title;
    }

    public class IndividualToken implements Token {
        public final String data;
        public final int position;
        private Scribe scribe = null;

        public IndividualToken(String data, int position) {
            this.data = Objects.requireNonNull(data);
            this.position = position;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof IndividualToken) {
                IndividualToken token = (IndividualToken) other;
                return IndividualTokenType.this.toString().equals(IndividualTokenType.this.toString())
                        && this.data.equals(token.data);
            } else {
                System.out.print("Cannot compare token with: ");
                System.out.println(other.toString());
                return false;
            }
        }

        @Override
        public String toString() {
            return "<" + getData() + ">";
        }

        public IndividualTokenType getType() {
            return IndividualTokenType.this;
        }

        @Override
        public Scribe getScribe() {
            return scribe != null ? scribe : getType().getScribe();
        }

        public void setScribe(Scribe scribe) {
            this.scribe = scribe;
        }

        @Override
        public Integer getPosition() {
            return position;
        }

        @Override
        public String getData() {
            return data;
        }

        @Override
        public IndividualToken getFirstHandle() {
            return this;
        }

        @Override
        public IndividualToken getLastHandle() {
            return this;
        }

        public class BrokenPiece extends IndividualToken {
            public BrokenPiece(int start, int end) {
                super(IndividualToken.this.getData().substring(start, end), IndividualToken.this.getPosition() + start);
            }

            public BrokenPiece(int start) {
                super(IndividualToken.this.getData().substring(start), IndividualToken.this.getPosition() + start);
            }
        }
    }
}
