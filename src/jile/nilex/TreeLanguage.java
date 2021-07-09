package jile.nilex;

import java.util.*;

import jile.nilex.IndividualTokenType.IndividualToken;
import jile.common.*;

public class TreeLanguage extends Language implements Singleton, Linter {

    private static TreeLanguage singleton;

    public static TreeLanguage singleton() {
        if (singleton == null) {
            singleton = new TreeLanguage();
        }
        return singleton;
    }

    private TreeLanguage() {
        super("Tree Language", "tlg", TokenizerMaker.singleton().maker.makeFrom("tree-language"));

        setMainMaker(treeMaker);
    }

    public final Maker<LinkedTree<String>> treeMaker = new Maker<LinkedTree<String>>() {
        @Override
        public LinkedTree<String> make(Code code) {
            Code.Process process = code.new Process("making a tree");
            LinkedTree<String> tree = new LinkedTree<String>();
            IndividualToken token;
            // Token valueToken;
            // String last;
            List<IndividualToken> tokens = DefaultTokenizer.tokenized.read(code);
            // System.out.println(tokens.toString().replaceAll(", ", ""));
            Iterator<IndividualToken> iterator = tokens.iterator();
            while (iterator.hasNext()) {
                token = iterator.next();
                switch (token.getTypeString()) {
                    case "verbatim <{>":
                        tree.goToLastAdded();
                        break;
                    case "verbatim <}>":
                        tree.goBack();
                        break;
                    case "node":
                        tree.add(token.data);
                        break;
                    // case "verbatim <hide>":
                    // tree.setSelectedNodeVisibility(false);
                    // break;
                    // case "verbatim <root>":
                    // tree.viewSelectedNodeAsRoot();
                    // break;
                    // case "node":
                    // last = token.data;
                    // tree.addAndSelect(last);
                    // break;
                    // case "letters": // "identifier"?
                    // valueToken = iterator.next();
                    // try {
                    // tree.setAttribute(token.data, Integer.parseInt(valueToken.data));
                    // } catch (NumberFormatException e) {
                    // process.add(new Tokenizer.Mishap.UnexpectedToken(token, valueToken.data));
                    // } catch (NoSuchFieldException e) {
                    // process.add(new Tokenizer.Mishap.UnexpectedToken(token));
                    // }
                    // break;
                    default:
                        process.issue(new UnexpectedToken(token));
                }
            }
            if (process.finish()) {
                return tree;
            } else {
                return null;
            }
        }
    };
}