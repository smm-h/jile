package jile.nilex;

import jile.nilex.CollectiveTokenType.CollectiveToken;

public interface Grouper {

    // public Tree<Group> aggroup(Code code);

    public static final Port<CollectiveToken> grouped = new Port<CollectiveToken>("Grouper:grouped");

}
