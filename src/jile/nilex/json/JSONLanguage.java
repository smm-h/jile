package jile.nilex.json;

import jile.common.Singleton;
import jile.common.Tree;
import jile.nilex.Code;
import jile.nilex.Language;
import jile.nilex.processors.Multiprocessor;

public class JSONLanguage extends Language implements Singleton {

    private static JSONLanguage singleton;

    public static JSONLanguage singleton() {
        if (singleton == null) {
            singleton = new JSONLanguage();
        }
        return singleton;
    }

    public JSONLanguage() {
        super("JSON", "json", new Multiprocessor());
    }

    public final Maker<Tree<JSON.Element>> maker = new Maker<Tree<JSON.Element>>() {
        @Override
        public Tree<JSON.Element> make(Code code) {
            // TODO Auto-generated method stub
            return null;
        }
    };
}
