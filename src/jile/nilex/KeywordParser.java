package jile.nilex;

import java.util.*;

import jile.nilex.Code.Process;

/**
 * Limitations:
 * <ul>
 * <li>Only works with {@link DefaultTokenizer.Verbatim} keywords.*</li>
 * <li>Keywords have to be entirely made up of lowercase letters.</li>
 * </ul>
 * <p>
 * Footnote:
 * <ul>
 * <li>*: These tokens are defined via {@link TokenizerMaker.Verbatim} objects,
 * which in turn are generated, usually from either
 * {@code verbatim 'somekeyword'} statements in {@code *.nlx} files or
 * single-quoted literal values in patterns of {@code *.ncx} files.</li>
 * </ul>
 */
abstract public class KeywordParser extends SinglePassParser {

    private final Set<String> keywords = new HashSet<String>();
    private final String expectation;

    public KeywordParser(ParsibleKeywords[] values) {
        String e = "";
        for (int i = 0; i < values.length; i++) {
            String keyword = values[i].toString().toLowerCase();
            keywords.add("verbatim <" + keyword + ">");
            if (!keyword.equals("not_a_keyword") && !keyword.equals("no_more_tokens")) {
                if (!e.equals(""))
                    e += "|";
                e += keyword;
            }
        }
        expectation = e;
    }

    abstract public class KeywordWalker extends CodeWalker {
        public KeywordWalker(Process process) {
            super(process);
        }

        abstract public ParsibleKeywords next();

        public String getNextKeyword() {
            if (hasNext()) {

                Token token = poll();

                if (token.is(keywords)) {
                    return token.getData().toUpperCase();
                } else {
                    getProcess().issue(new UnexpectedToken(token, expectation));
                    return "NOT_A_KEYWORD";
                }
            } else {
                return "NO_MORE_TOKENS";
            }
        }
    }

    /**
     * Implement this interface only with enums whose values correspond to your
     * keywords but are all in caps. Also you must include these two values:
     * {@code NO_MORE_TOKENS}, {@code NOT_A_KEYWORD}.
     * 
     * @see KeywordParser
     */
    public interface ParsibleKeywords {
    }
}