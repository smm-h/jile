package jile.nilex;

import jile.vis.Viewer;

public class TextLanguage extends Language {

    private static TextLanguage singleton;

    public static TextLanguage singleton() {
        if (singleton == null) {
            singleton = new TextLanguage();
        }
        return singleton;
    }

    public TextLanguage() {
        super("Text Language", "txt", TokenizerMaker.singleton().maker.makeFrom("text-language"));
    }

    public static void main(String[] args) {
        // Codestack.highlight("nlx/Plain Text Language.nlx");
        Viewer.singleton().show(new Code("contents", singleton()).visualize());
    }
}
