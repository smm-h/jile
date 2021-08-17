package jile.nlp;

abstract public class OneToOneRomanizer<L extends NaturalLanguage> implements Romanizer<L> {
    abstract public char romanize(Morpheme<L> morpheme);

    abstract public Morpheme<L> unromanize(char character);

    public String romanize(Morpheme<L>[] morphemeArray) {
        int n = morphemeArray.length;
        StringBuilder builder = new StringBuilder(n);
        for (int i = 0; i < n; i++)
            builder.append(romanize(morphemeArray[i]));
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    public Morpheme<L>[] unromanize(String string) {
        Morpheme<L>[] morphemeArray = new Morpheme[string.length()];
        for (char i : string.toCharArray())
            morphemeArray[i] = unromanize(i);
        return morphemeArray;
    }
}
