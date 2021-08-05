package jile.lingu;

import jile.lingu.IndividualTokenType.IndividualToken;
import jile.common.Tree;
import jile.vis.Scribe;

public interface Token {

    public Scribe getScribe();

    default public String getTypeString() {
        return getType().toString();
    }

    default public boolean is(String type) {
        return getTypeString().equals(type);
    }

    default public boolean is(String... types) {
        for (int i = 0; i < types.length; i++) {
            if (is(types[i])) {
                return true;
            }
        }
        return false;
    }

    default public boolean is(Iterable<String> types) {
        for (String type : types) {
            if (is(type)) {
                return true;
            }
        }
        return false;
    }

    public IndividualToken getFirstHandle();

    public IndividualToken getLastHandle();

    public static final Port<Tree<Token>> tree = new Port<Tree<Token>>("Token:tree");

    public TokenType getType();

    public Integer getPosition();

    public String getData();
}
