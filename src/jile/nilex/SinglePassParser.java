package jile.nilex;

import java.util.*;

import jile.nilex.Code.Process;
import jile.nilex.CollectiveTokenType.CollectiveToken;
import jile.common.Common;
import jile.nilex.IndividualTokenType.IndividualToken;
import jile.nilex.processors.SingleProcessor;

/**
 * Limitations:
 * <ul>
 * <li>Only works with {@link DefaultTokenizer.Verbatim} keywords.*</li>
 * <li>Keywords have to be made up entirely of lowercase letters.</li>
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
abstract public class SinglePassParser extends SingleProcessor {

    private final Map<String, CollectiveToken> namedGroups = new HashMap<String, CollectiveToken>();

    public void nameGroup(String name, CollectiveToken block) {
        namedGroups.put(name, Objects.requireNonNull(block));
    }

    public CollectiveToken findGroup(String name) {
        if (name == null)
            return null;
        else
            return namedGroups.get(name);
    }

    public interface Resolver {
        public boolean canBeResolved(String identifier);
    }

    abstract public class CodeWalker {

        private final Code.Process process;

        public CodeWalker(Process process) {
            this.process = process;
        }

        /** must never be null */
        private Token[] array = new Token[0];

        private int index;

        private final Stack<CollectiveToken> blocks = new Stack<CollectiveToken>();
        private final Stack<Integer> blockIndices = new Stack<Integer>();

        public Code.Process getProcess() {
            return process;
        }

        public int getDepth() {
            return blocks.size();
        }

        public void enter(CollectiveToken node) {

            Objects.requireNonNull(node, "cannot enter null group");

            // push the new block on the stack
            blocks.push(node);

            // remember the position of the index for the outer block
            blockIndices.push(index);

            // reset the index for the inner block
            index = 0;

            // remake the array
            remakeArray();
        }

        // public class CannotEnterNullGroup extends SinglePassParserMishap {

        // public CannotEnterNullGroup(IndividualToken token) {
        // super(token, true);
        // }

        // @Override
        // public String getReport() {
        // return "Cannot enter null group";
        // }
        // }

        public void reenter() {
            index = 0;
        }

        public void exit() {

            // pop the inner block off the stack
            blocks.pop();

            // remember where the index was when we entered that other block
            index = blockIndices.pop();

            // remake the array
            remakeArray();
        }

        /**
         * If peek is what it "must be", meaning if it is not issue an appropriate
         * mishap.
         */
        public boolean peekMustBe(String peekingFor, String... types) {
            Token token = peek(peekingFor);
            if (token == null) {
                return false;
            } else if (token.is(types)) {
                return true;
            } else {
                process.issue(new UnexpectedToken(token.getFirstHandle(), String.join("|", types)));
                return false;
            }
        }

        public Token peek(String peekingFor) {
            return peek(peekingFor, false);
        }

        /**
         * Returns the next available token. However, if the end of the block has been
         * reached, if {@code optional} is false a {@code null} is returned, and if it
         * is true a {@link NullToken} will be returned, to avoid unnecessary
         * {@code NullPointerException}s. So either set optional to true, or prepare for
         * getting a {@code null}.
         */
        public Token peek(String peekingFor, boolean optional) {
            if (array.length == 0 || index >= array.length) {
                if (optional) {
                    return VirtualTokenType.singleton().nullToken;
                } else {
                    process.issue(new UnexpectedEndOfBlock(peekingFor));
                    // index++;
                    return null;
                }
            } else {
                return array[index];
            }
        }

        /**
         * <b>IMPORTANT</b> You must always call either {@code hasNext()} or
         * {@code peek}/{@code peekMustBe} before polling, and only do so if
         * {@code hasNext()} returns true, or {@code peek}/{@code peekMustBe} returns
         * non-null.
         */
        public Token poll() {
            return array[index++];
        }

        public boolean hasNext() {
            return index < array.length;
        }

        private void remakeArray() {

            if (blocks.isEmpty()) {
                array = new Token[0];

            } else {
                List<Token> list = blocks.peek().getChildren();
                array = new Token[list.size()];

                int i = 0;
                for (Token token : list)
                    array[i++] = token;
            }
        }

        /** Use when exactly one or the other of two verbatims is expected. */
        public Boolean parseDichotomy(String vtrue, String vfalse) {
            if (peekMustBe(vtrue + "|" + vfalse, "verbatim <" + vtrue + ">", "verbatim <" + vfalse + ">")) {
                return poll().is("verbatim <" + vtrue + ">");
            } else {
                return null;
            }
        }

        /**
         * Either returns a group, or return null and issues an approriate mishap, i.e.
         * it is safe to ignore null outcomes.
         */

        public CollectiveToken parseGroup(String type) {
            return parseGroup(type, null);
        }

        /**
         * Either returns a group, or return null and issues an approriate mishap, i.e.
         * it is safe to ignore null outcomes. You can also name the block.
         */

        public CollectiveToken parseGroup(String type, String name) {
            if (peekMustBe(type, "group_type <" + type + ">")) {
                var group = (CollectiveToken) poll();
                if (name != null)
                    nameGroup(name, group);
                return group;
            } else {
                return null;
            }
        }

        /**
         * Either returns null and issues a fatal mishap, or returns a group identifier.
         * This means you can safely ignore a null outcome.
         */
        public String parseGroupIdentifier() {
            return parseIdentifier(namedGroups);
        }

        /**
         * Use when expecting an identifier.
         */
        public String parseIdentifier() {
            return parseIdentifier((Resolver) null);
        }

        /**
         * Use when expecting an identifier. Provide the appropriate {@link Map} object
         * to assert it must contain it.
         */
        public String parseIdentifier(Map<String, ?> map) {
            return parseIdentifier(map == null ? null : new Resolver() {
                @Override
                public boolean canBeResolved(String identifier) {
                    return map.containsKey(identifier);
                }
            });
        }

        /**
         * Use when expecting an identifier. Provide the appropriate {@link Resolver}
         * object to assert it must resolve it.
         */
        public String parseIdentifier(Resolver resolver) {
            if (peekMustBe("identifier", "identifier")) {
                Token token = poll();
                String identifier = token.getData();
                if (resolver != null && !resolver.canBeResolved(identifier)) {
                    getProcess().issue(new IdentifierNotResolved((IndividualToken) token));
                    return null;
                } else {
                    return identifier;
                }
            } else {
                return null;
            }
        }

        /**
         * Returns true if the sign is negative, and false if it is positive or no sign
         * is present.
         */
        public boolean parseSign() {
            if (peek("sign", true).is("verbatim <->", "verbatim <+>"))
                return poll().is("verbatim <->");
            return false;
        }

        public Integer parseSignedInteger() {
            boolean negative = parseSign();
            return parseUnsignedInteger() * (negative ? -1 : +1);
        }

        public Float parseSignedFloat() {
            boolean negative = parseSign();
            return parseUnsignedFloat() * (negative ? -1 : +1);
        }

        public Integer parseUnsignedInteger() {
            return peekMustBe("digits", "digits") ? (int) Common.valueOfString(poll().getData()) : null;
        }

        public Float parseUnsignedFloat() {

            // there must be digits
            if (peekMustBe("digits", "digits")) {
                String string = poll().getData();

                // there may be a dot
                if (peek("dot", true).is("verbatim <.>")) {
                    poll();

                    // if so, there must be digits again
                    if (peekMustBe("digits", "digits")) {
                        string += "." + poll().getData();
                    }
                }

                return Common.valueOfString(string).floatValue();
            } else {
                return null;
            }
        }

        public class UnexpectedEndOfBlock extends SinglePassParserMishap {

            private final String expectation;

            public UnexpectedEndOfBlock(String expectation) {
                super(blocks.peek().closer, true);
                this.expectation = expectation;
            }

            public String getReport() {
                return "Unexpected end of block; instead was expecting: `" + expectation + "`";
            }
        }
    }

    abstract public class SinglePassParserMishap extends Mishap {

        public SinglePassParserMishap(IndividualToken token, boolean fatal) {
            super(token, fatal);
        }
    }

    public class IdentifierNotResolved extends SinglePassParserMishap {

        public IdentifierNotResolved(IndividualToken token) {
            super(token, true);
        }

        @Override
        public String getReport() {
            return "Identifier `" + token.getData() + "` could not be resolved.";
        }
    }
}