package jile.lingu;

import java.util.*;

import jile.common.Resource;
import jile.lingu.processors.Processor;

abstract public class Language {

    public final String name, langPath, primaryExt;

    public final Processor processor;

    public Language(String name, String langPath, String primaryExt, Processor processor) {
        this.name = name;
        this.langPath = langPath;
        this.primaryExt = primaryExt;
        this.processor = processor;

        // if (processor instanceof Tokenizer)
        // setMainTokenizer((Tokenizer) processor);

        if (primaryExt != null)
            Languages.singleton().associateExtWithLanguage(primaryExt, this);
    }

    public Language(String name, String primaryExt, Processor processor) {
        this(name, primaryExt, primaryExt, processor);
    }

    public Resource find(String address) {
        return Objects.requireNonNull(Resource.of(langPath + "/" + address + "." + primaryExt));
    }

    private Maker<?> mainMaker;

    public void setMainMaker(Maker<?> maker) {
        this.mainMaker = maker;
    }

    public Maker<?> getMainMaker() {
        return mainMaker;
    }

    abstract public class Maker<T> implements Linter {

        abstract public T make(Code code);

        public T makeFrom(String address) {
            return make(new Code(find(address), Language.this));
        };

    }
}