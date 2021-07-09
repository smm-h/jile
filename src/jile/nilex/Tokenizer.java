package jile.nilex;

import java.util.List;

import jile.nilex.IndividualTokenType.IndividualToken;

public interface Tokenizer {
    public List<IndividualToken> tokenize(Code code);

    // public void ignore(String name)

    // public boolean shouldBeIgnored(Token token)

    // private boolean canMake(List<Token> tokens, int index, List<Token> pattern)

    public static final Port<List<IndividualToken>> tokenized = new Port<List<IndividualToken>>("Tokenizer:tokenized");
}