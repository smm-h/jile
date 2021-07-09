package jile.nilex;

import java.util.*;

import jile.common.*;
import jile.vis.*;

import jile.nilex.IndividualTokenType.IndividualToken;
import jile.nilex.TokenizerMaker.Definition;

// TODO normalizations e.g. − to - and → to ->
public class DefaultTokenizer extends SingleProcessor implements Tokenizer {

    private final HashMap<Character, TreeSet<Kept>> kepts = new HashMap<Character, TreeSet<Kept>>();

    // each character may belong to zero, one, or more streaks
    private final HashMap<Character, TreeSet<Streak>> streaksOfCharacters = new HashMap<Character, TreeSet<Streak>>();
    private final HashSet<Character> verbatimCharacters = new HashSet<Character>();
    private final HashMap<String, TreeSet<Verbatim>> verbatims = new HashMap<String, TreeSet<Verbatim>>();
    private final HashSet<String> ignorableNames = new HashSet<String>();

    public boolean ignore(String name) {
        if (ignorableNames.contains(name)) {
            namesToIgnore.add(name);
            // System.out.println("IGNORE: " + name);
            ignorableNames.remove(name);
            return true;
        } else {
            return false;
        }
    }

    public void _process(Code code) {

        List<IndividualToken> stripped = new LinkedList<IndividualToken>();
        List<IndividualToken> drawable = new LinkedList<IndividualToken>();

        for (IndividualToken token : tokenize(code)) {
            drawable.add(token);
            if (!shouldBeIgnored(token))
                stripped.add(token);
        }

        Code.syntax.write(code, drawable);

        DefaultTokenizer.tokenized.write(code, stripped);
    }

    private PriorityQueue<Definition> schedule = new PriorityQueue<Definition>(new Comparator<Definition>() {

        @Override
        public int compare(Definition one, Definition other) {
            return ((Integer) one.getPriority()).compareTo(other.getPriority());
        }

    });

    public void schedule(Definition definition) {
        schedule.add(definition);
    }

    public void defineAsScheduled() {
        while (!schedule.isEmpty()) {
            Definition definition = schedule.poll();
            define(definition);
        }
    }

    private void addIgnorableName(String name) {
        ignorableNames.add(name);
    }

    private LinkedList<String> namesToIgnore = new LinkedList<String>();

    private boolean canMake(List<IndividualToken> tokens, int index, List<IndividualToken> pattern) {
        for (int i = 0; i < pattern.size(); i++) {
            if (index + i >= tokens.size())
                return false;
            if (!pattern.get(i).equals(tokens.get(index + i)))
                return false;
        }
        return true;
    }

    public boolean shouldBeIgnored(IndividualToken token) {
        return namesToIgnore.contains(token.getTypeString());
    }

    // TODO make this the convention

    public IndividualTokenType define(Definition definition) {

        if (definition instanceof TokenizerMaker.Streak)
            return define((TokenizerMaker.Streak) definition);

        else if (definition instanceof TokenizerMaker.Kept)
            return define((TokenizerMaker.Kept) definition);

        else if (definition instanceof TokenizerMaker.Verbatim)
            return define((TokenizerMaker.Verbatim) definition);

        // no more define-ables
        else
            return null;
    }

    public Streak define(TokenizerMaker.Streak definition) {
        Streak type = new Streak(definition);
        addIgnorableName(definition.toString());
        for (char c : definition.characters) {
            if (!streaksOfCharacters.containsKey(c)) {
                streaksOfCharacters.put(c, new TreeSet<Streak>());
            }
            streaksOfCharacters.get(c).add(type);
        }
        return type;
    }

    public Verbatim define(TokenizerMaker.Verbatim definition) {
        // addIgnorableName(verbatim.toString());
        // VERBATIMS SHOULD NOT BE IGNORED
        // TODO repeated verbatims should not go through
        // but should still return the TokenType
        Verbatim type = new Verbatim(definition, makeupStreaks(0, definition.data));
        for (IndividualToken token : type.pattern) {
            if (token.getType() instanceof NonStreakCharacter) {
                verbatimCharacters.add(token.data.charAt(0));
            }
        }

        String key = type.pattern.getFirst().data;
        if (!verbatims.containsKey(key)) {
            verbatims.put(key, new TreeSet<Verbatim>());
        }
        verbatims.get(key).add(type);
        return type;
    }

    public Kept define(TokenizerMaker.Kept definition) {
        Kept type = new Kept(definition);
        addIgnorableName(definition.toString());
        Character key = definition.opener.charAt(0);
        if (!kepts.containsKey(key)) {
            kepts.put(key, new TreeSet<Kept>());
        }
        kepts.get(key).add(type);
        return type;
    }

    @Override
    public List<IndividualToken> tokenize(Code code) {

        // start the process
        var tokenizing = code.new Process("tokenizing");

        // get the contents of the code as a string
        String string = code.getString();

        // create a new list to put the tokens in
        List<IndividualToken> tokens = new LinkedList<IndividualToken>();

        // keep track of where (in which kept, if any) we are in the code
        Kept where = null;

        // keep track of which character we are looking at
        int flag = 0;

        // keep track of what portion of the code we have added all the tokens of
        int done = 0;

        // loop through all of the characters in the code
        while (flag < string.length()) {

            // if we are not inside a kept
            if (where == null) {

                // search for a kept to enter
                Kept found = null;

                // get the character at the forward flag
                char character = string.charAt(flag);

                // if we have a kept that starts with that character
                if (kepts.containsKey(character)) {

                    // search all kepts that start with that character
                    for (Kept kept : kepts.get(character)) {

                        // figure out where the opener of that kept would end if it had appeared
                        int endIndex = flag + kept.opener.length();

                        // if that opener can appear and does appear
                        if (endIndex <= string.length() && string.substring(flag, endIndex).equals(kept.opener)) {

                            // we found our kept
                            found = kept;

                            // so stop the search
                            break;
                        }
                    }
                }

                // if we found a kept that we must enter
                if (found != null) {

                    // take whatever is before it, break it into tokens, and add them
                    for (IndividualToken token : makeupVerbatims(makeupStreaks(done, string.substring(done, flag)))) {
                        if (token.getType() instanceof NonStreakCharacter)
                            tokenizing.issue(new UnknownCharacter(token));
                        tokens.add(token);
                    }

                    // enter that kept
                    where = found;

                    // add the opener to this kept
                    tokens.add(where.keeper.new IndividualToken(where.opener, flag));

                    // move the flag forward
                    flag += where.opener.length() - 1;

                    // everything until here is added so also move the backward flag
                    done = flag + 1;
                }
            }

            // if we are inside a kept
            else {

                // figure out where its closer would end if it had appeared
                int endIndex = flag + where.closer.length();

                // if that closer can appear and does appear
                if (endIndex <= string.length() && string.substring(flag, endIndex).equals(where.closer)) {

                    // add the kept
                    tokens.add(where.new IndividualToken(string.substring(done, flag), done));

                    // also add its closer
                    tokens.add(where.keeper.new IndividualToken(where.closer, done));

                    // TODO test a kept with an at least 3 characters long closer

                    // move the flag forward
                    flag = endIndex - 1;

                    // everything until here is added so also move the backward flag
                    done = flag + 1;

                    // exit the kept
                    where = null;
                }
            }

            // move the flag over to the next character
            flag++;
        }

        // when the flag reaches the end, not everything has been added
        if (done < string.length()) {

            // either a non-kept portion remains
            if (where == null) {

                // break it into tokens and add them
                for (IndividualToken token : makeupVerbatims(makeupStreaks(done, string.substring(done, flag)))) {
                    if (token.getType() instanceof NonStreakCharacter)
                        tokenizing.issue(new UnknownCharacter(token));
                    tokens.add(token);
                }
            }

            // or an unclosed kept remains
            else {

                // unless its closer is entirely whitespace,
                if (!where.closer.isBlank())

                    // attach a mishap to its opener
                    tokenizing.issue(new UnclosedKept(tokens.get(tokens.size() - 1)));

                // and add it
                tokens.add(where.new IndividualToken(string.substring(done, flag), done));
            }
        }

        // finish the process
        tokenizing.finish();

        // and return the tokens
        return tokens;
    }

    public LinkedList<IndividualToken> makeupVerbatims(LinkedList<IndividualToken> tokens) {
        int index = 0, length;
        IndividualToken token;
        PriorityQueue<Verbatim> possibles;
        Verbatim longestPossible;
        while (index < tokens.size()) {

            // get t[i]
            token = tokens.get(index);

            // boolean reporting = token.data.equals("turn") || token.data.equals("{");
            // String report = "";

            // if (reporting)
            // report += "found at index: " + index;

            // V = all verbatims that start with t[i], sorted by length
            if (verbatims.containsKey(token.data)) {
                possibles = new PriorityQueue<Verbatim>(verbatims.get(token.data));
                // System.out.println(possibles);

                // if (reporting)
                // report += ", possibles: " + possibles;

                // do v = V.pop() while v cannot be made and V is not empty
                while (!possibles.isEmpty()) {
                    longestPossible = possibles.remove();
                    // System.out.println(longestPossible);

                    // if (token.data.equals(".")) {
                    // if (index == 1) {
                    // System.out.println(tokens);
                    // System.out.println(longestPossible.pattern);
                    // System.out.println(index);
                    // for (int i = 0; i < 3; i++) {
                    // Token temp;
                    // // System.out.print(tokens.get(i + index));
                    // System.out.print("P: ");
                    // temp = longestPossible.pattern.get(i);
                    // System.out.print(temp.data);
                    // System.out.print(" ");
                    // System.out.print(temp.type);
                    // System.out.print("\tA: ");
                    // temp = tokens.get(i + index);
                    // System.out.print(temp.data);
                    // System.out.print(" ");
                    // System.out.print(temp.type);
                    // System.out.print("\n");
                    // }
                    // }
                    // }

                    // if v can be made, make it, and i += len(v)
                    if (canMake(tokens, index, longestPossible.pattern)) {
                        length = longestPossible.pattern.size();
                        for (int i = 0; i < length; i++) {
                            tokens.remove(index);
                        }
                        tokens.add(index, makeToken(longestPossible, token.position));
                        index += length - 1;
                        break;
                    }
                }
            }
            index++;
            // if (reporting)
            // report += ", new index: " + index;
            // if (reporting)
            // System.out.println(report);
        }
        return tokens;
    }

    private IndividualToken makeToken(DefaultTokenizer.Verbatim verbatim, int position) {
        return verbatim.new IndividualToken(verbatim.data, position);
    }

    private IndividualToken makeToken(DefaultTokenizer.NonStreakCharacter nsc, int position) {
        return nsc.new IndividualToken(Character.toString(nsc.data), position);
    }

    private LinkedList<IndividualToken> makeupStreaks(int offset, String string) {
        // String report = "\t" + "[" + string + "]===";
        string += (char) 0;
        char character;
        String data = "";
        TreeSet<Streak> last, curr = new TreeSet<Streak>();
        int index = 0;
        LinkedList<IndividualToken> tokens = new LinkedList<IndividualToken>();
        while (index < string.length()) {
            character = string.charAt(index);

            // if (character == '\n')
            // character = '#';

            last = curr;

            if (streaksOfCharacters.containsKey(character)) {
                curr = new TreeSet<Streak>(streaksOfCharacters.get(character));
            } else {
                curr = new TreeSet<Streak>();
                if (data.equals("")) {
                    tokens.add(makeToken(new NonStreakCharacter(character), offset + index));
                    // report += "<" + character + ">";
                }
            }

            if (!last.isEmpty()) {
                curr.retainAll(last);
            }

            if (curr.isEmpty()) {
                if (!last.isEmpty()) {
                    tokens.add(last.first().new IndividualToken(data, offset + index - data.length()));
                    // report += "[" + data + "]";
                    index--;
                }
                data = "";
            } else {
                data += character;
            }

            index++;
        }
        tokens.removeLast();
        // System.out.println(report);
        return tokens;
    }

    private static final Scribe scribeOfNonStreakCharacter = CodeView.makeScribe(new Dye(0.95f, -0.25f),
            Scribe.Style.NEITHER);

    public class NonStreakCharacter extends IndividualTokenType {

        char data;

        public NonStreakCharacter(char c) {
            super("unknown_character");
            this.data = c;
            setScribe(scribeOfNonStreakCharacter);
        }
    }

    private static final Scribe scribeOfVerbatim = CodeView.makeScribe(new Dye(0.65f, -0.95f), Scribe.Style.BOLD);

    public class Verbatim extends IndividualTokenType implements Comparable<Verbatim> {

        public final String data;
        public final LinkedList<IndividualToken> pattern;

        public Verbatim(TokenizerMaker.Verbatim definition, LinkedList<IndividualToken> pattern) {
            super(definition.title);
            this.data = definition.data;
            this.pattern = pattern;
            setScribe(scribeOfVerbatim);
        }

        public int compareTo(Verbatim other) {
            int comparison = -((Integer) (this.data.length())).compareTo(other.data.length());
            if (comparison == 0)
                comparison = this.data.compareTo(other.data);
            return comparison;
        }

        public boolean equals(Verbatim other) {
            return data.equals(other.data);
        }
    }

    private static final Scribe scribeOfStreak = CodeView.makeScribe(new Dye(0.1f, -0.95f), Scribe.Style.NEITHER);

    public class Streak extends IndividualTokenType implements Comparable<Streak> {

        public final Set<Character> characters = new HashSet<Character>();

        public Streak(TokenizerMaker.Streak definition) {
            super(definition.title);
            characters.addAll(definition.characters);
            setScribe(scribeOfStreak);
        }

        public int compareTo(Streak other) {
            int comparison = ((Integer) (this.characters.size())).compareTo(other.characters.size());
            if (comparison == 0)
                comparison = ((Integer) this.characters.hashCode()).compareTo(other.characters.hashCode());
            return comparison;
        }
    }

    private static final Scribe scribeOfKept = CodeView.makeScribe(new Dye(0.3f, -1), Scribe.Style.ITALIC);

    public class Kept extends IndividualTokenType implements Comparable<Kept> {

        public final String opener, closer;

        public Kept(TokenizerMaker.Kept definition) {
            super(definition.title);
            this.closer = definition.closer;
            this.opener = definition.opener;
            setScribe(scribeOfKept);
            keeper = new Keeper(definition.title + "_keeper");
        }

        // public String (IndividualToken token)
        // return opener + token.data + closer;

        public int compareTo(Kept other) {
            int comparison = -((Integer) this.opener.length()).compareTo(other.opener.length());
            if (comparison == 0)
                comparison = this.opener.compareTo(other.opener);
            return comparison;
        }

        private final Keeper keeper;

        public class Keeper extends IndividualTokenType {
            public Keeper(String title) {
                super(title);
                namesToIgnore.add(title);
                setScribe(scribeOfKept);
            }
        }
    }

    abstract public class TokenizerMishap extends Mishap {
        public TokenizerMishap(IndividualToken token, boolean fatal) {
            super(token, fatal);
        }
    }

    public class UnknownCharacter extends TokenizerMishap {

        public UnknownCharacter(IndividualToken token) {
            super(token, true);
            assert token.getType() instanceof NonStreakCharacter;
        }

        public String getReport() {
            int cp = token.getData().codePointAt(0);
            if (cp > 32)
                return "Unknown character `" + token.getData() + "`";
            else
                return "Unknown non-printable character `" + Common.codepointToText(token.getData().codePointAt(0))
                        + "`";
        }
    }

    public class UnclosedKept extends TokenizerMishap {
        public UnclosedKept(IndividualToken token) {
            super(token, true);
            assert token.getType() instanceof Kept.Keeper;
        }

        public String getReport() {
            return "Opened but never closed";
        }
    }
}