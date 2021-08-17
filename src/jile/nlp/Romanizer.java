package jile.nlp;

public interface Romanizer<L extends NaturalLanguage> {
    public char romanize(Morpheme<L> morpheme);

    public Morpheme<L> unromanize(char character);

    public String romanize(Morpheme<L>[] morphemeArray);

    public Morpheme<L>[] unromanize(String string);
}
