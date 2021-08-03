package jile.nilex;

import java.util.*;

import jile.common.*;

public class Languages implements Singleton {

    private Languages() {
    }

    private static Languages singleton;

    public static Languages singleton() {
        if (singleton == null) {
            singleton = new Languages();
        }
        return singleton;
    }

    private final HashMap<String, Language> extToLanguage = new HashMap<String, Language>();

    public void associateExtWithLanguage(String ext, Language language) {
        // if (extToLanguage == null) {
        // extToLanguage = new HashMap<String, Language>();
        // }
        ext = ext.toLowerCase();
        extToLanguage.put(ext, language);
        // System.out.println("*." + ext + " <- " + language.name);
    }

    public Language getLanguageByExt(String ext) {
        Language language = null;
        if (extToLanguage != null) {
            ext = ext.toLowerCase();
            if (extToLanguage.containsKey(ext)) {
                language = extToLanguage.get(ext);
            }
        }
        return language;
    }
}
