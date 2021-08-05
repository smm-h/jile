package jile.lingu;

import java.util.List;

import jile.lingu.IndividualTokenType.IndividualToken;
import jile.lingu.processors.Processor;

public interface Tokenizer extends Processor {
    public List<IndividualToken> tokenize(Code code);

    // public void ignore(String name)

    // public boolean shouldBeIgnored(Token token)

    // private boolean canMake(List<Token> tokens, int index, List<Token> pattern)

    public static final Port<List<IndividualToken>> tokenized = new Port<List<IndividualToken>>("Tokenizer:tokenized");
}