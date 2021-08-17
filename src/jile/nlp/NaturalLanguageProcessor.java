package jile.nlp;

import java.util.Set;

abstract public class NaturalLanguageProcessor<L extends NaturalLanguage> {
    private final Set<Morpheme<L>> morphemes;

    public NaturalLanguageProcessor(Set<Morpheme<L>> morphemes) {
        this.morphemes = morphemes;
    }
}
