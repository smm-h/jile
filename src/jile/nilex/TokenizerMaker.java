package jile.nilex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import jile.nilex.IndividualTokenType.IndividualToken;
import jile.nilex.processors.Multiprocessor;
import jile.nilex.processors.SingleProcessor;
import jile.common.Common;
import jile.common.Resource;
import jile.common.Singleton;

public class TokenizerMaker extends Language implements Singleton {

    private static TokenizerMaker singleton;

    public static TokenizerMaker singleton() {
        if (singleton == null) {
            singleton = new TokenizerMaker();
        }
        return singleton;
    }

    private final DefaultTokenizer meta;

    private TokenizerMaker() {
        super("Tokenizer Maker", "nlx", new Multiprocessor());

        meta = new DefaultTokenizer();

        meta.define(new Kept("single_quotes", "'", "'"));
        meta.define(new Kept("double_quotes", "\"", "\""));

        meta.define(new Kept("comment", "#", "\n"));
        meta.ignore("comment");
        meta.define(new Kept("single_line_comment", "//", "\n"));
        meta.ignore("single_line_comment");
        meta.define(new Kept("multi_line_comment", "/*", "*/"));
        meta.ignore("multi_line_comment");

        meta.define(new Streak("whitespace", " \t\r\n\f"));
        meta.ignore("whitespace");

        meta.define(new Streak("identifier", "[0-9][A-Z][a-z]_-"));

        // meta.define(new Verbatim("ext"));
        meta.define(new Verbatim("import"));
        meta.define(new Verbatim("keep"));
        meta.define(new Verbatim("ignore"));
        meta.define(new Verbatim("verbatim"));
        meta.define(new Verbatim("streak"));
        meta.define(new Verbatim("..."));
        meta.define(new Verbatim("as"));

        processor.extend(meta);

        processor.extend(new SingleProcessor() {

            @Override
            public void process(Code code) {

                var links = new HashMap<IndividualToken, Resource>();
                var iterator = DefaultTokenizer.tokenized.read(code).iterator();

                while (iterator.hasNext()) {
                    IndividualToken token = iterator.next();
                    if (token.is("verbatim <import>")) {
                        token = iterator.next();
                        links.put(token, TokenizerMaker.singleton.find(token.data));
                    }
                }

                Code.links.write(code, links);
                // TODO update instead of over-writing
            }
        });
    }

    // public static final Port<Tokenizer> port = new Port<Tokenizer>();

    public final Maker<DefaultTokenizer> maker = new Maker<DefaultTokenizer>() {
        public DefaultTokenizer make(Code code) {
            // Tokenizer tokenizer = augment(new Tokenizer(), code);
            // port.write(code, tokenizer);
            // return tokenizer;
            return augment(new DefaultTokenizer(), code);
        }

        private DefaultTokenizer augment(DefaultTokenizer beingMade, Code code) {

            var making = code.new Process("making a tokenizer");

            Objects.requireNonNull(beingMade);

            // System.out.println(language.name + " <+ " + code.getIdentity());

            String _string[] = { "single_quotes", "double_quotes" };
            // System.out.println(tokens.toString().replaceAll(", ", ""));
            List<IndividualToken> tokens = DefaultTokenizer.tokenized.read(code);
            // System.out.println(tokens);
            Iterator<IndividualToken> iterator = tokens.iterator();
            IndividualToken head, tail;
            while (iterator.hasNext()) {
                head = iterator.next();
                String opener, closer, title, characters;
                switch (head.getTypeString()) {
                    case "verbatim <import>":
                        tail = iterator.next();
                        switch (tail.getTypeString()) {
                            case "single_quotes":
                            case "double_quotes":
                                try {
                                    augment(beingMade, new Code(Resource.of("nlx/" + tail.data + ".nlx")));
                                } catch (Exception e) {
                                    // process.add(meta.new ImportFailed());
                                    System.out.println("import failed");
                                }
                                break;
                            default:
                                making.issue(new UnexpectedToken(head, tail, "string"));
                        }
                        break;
                    // case "verbatim <ext>":
                    // tail = iterator.next();
                    // switch (tail.type.toString()) {
                    // case "single_quotes":
                    // case "double_quotes":
                    // if (!isImporting())
                    // Languages.singleton().associateExtWithLanguage(tail.data, language);
                    // break;
                    // default:
                    // process.add(meta.new UnexpectedToken(head, tail, "string"));
                    // }
                    // break;
                    case "verbatim <ignore>":
                        tail = iterator.next();
                        if (!tail.is("identifier")) {
                            making.issue(new UnexpectedToken(tail, "identifier"));
                            break;
                        }
                        beingMade.ignore(tail.data);
                        break;
                    case "verbatim <keep>":
                        tail = iterator.next();
                        if (!tail.is(_string)) {
                            making.issue(new UnexpectedToken(tail, "string"));
                            break;
                        }
                        opener = Common.escape(tail.data);
                        tail = iterator.next();
                        if (!tail.is("verbatim <...>")) {
                            making.issue(new UnexpectedToken(tail, "<...>"));
                            break;
                        }
                        tail = iterator.next();
                        if (!tail.is(_string)) {
                            making.issue(new UnexpectedToken(tail, "string"));
                            break;
                        }
                        closer = Common.escape(tail.data);
                        tail = iterator.next();
                        if (!tail.is("verbatim <as>")) {
                            making.issue(new UnexpectedToken(tail, "<as>"));
                            break;
                        }
                        tail = iterator.next();
                        if (!tail.is("identifier")) {
                            making.issue(new UnexpectedToken(tail, "identifier"));
                            break;
                        }
                        title = tail.data;
                        beingMade.define(new Kept(title, opener, closer));
                        break;
                    case "verbatim <streak>":
                        tail = iterator.next();
                        switch (tail.getTypeString()) {
                            case "single_quotes":
                            case "double_quotes":
                                characters = tail.data;
                                if (!tail.is(_string)) {
                                    making.issue(new UnexpectedToken(tail, "string"));
                                    break;
                                }
                                tail = iterator.next();
                                if (!tail.is("verbatim <as>")) {
                                    making.issue(new UnexpectedToken(tail, "<as>"));
                                    break;
                                }
                                tail = iterator.next();
                                if (!tail.is("identifier")) {
                                    making.issue(new UnexpectedToken(tail, "identifier"));
                                    break;
                                }
                                title = tail.data;
                                beingMade.define(new Streak(title, Common.escape(characters)));
                                break;
                            default:
                                making.issue(new UnexpectedToken(head, tail, "string|identifier"));
                        }
                        break;
                    case "verbatim <verbatim>":
                        tail = iterator.next();
                        switch (tail.getTypeString()) {
                            case "single_quotes":
                            case "double_quotes":
                                beingMade.define(new Verbatim(Common.escape(tail.data)));
                                break;
                            default:
                                making.issue(new UnexpectedToken(head, tail, "string"));
                        }
                        break;
                    default:
                        making.issue(new UnexpectedToken(head));
                }
            }
            making.finish();
            return beingMade;
        }
    };

    abstract public class Definition {
        public final String title;

        public Definition(String title) {
            this.title = title;
        }

        @Override
        public final String toString() {
            return title;
        }

        abstract public int getPriority();
    }

    public class Verbatim extends Definition implements Comparable<Verbatim> {
        public final String data;

        public Verbatim(String data) {
            super("verbatim <" + data + ">");
            this.data = data;
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

        @Override
        public int getPriority() {
            return 30;
        }
    }

    // private static final String NEGATOR = "(anything-except)";

    public class Streak extends Definition implements Comparable<Streak> {
        public final HashSet<Character> characters = new HashSet<Character>();

        public Streak(String title, String characters) {
            super(title);

            characters = characters.replaceAll("\\[A-Z\\]", "ABCDEFGHIJKLMNOPQRSTUVWXYZ")
                    .replaceAll("\\[a-z\\]", "abcdefghijklmnopqrstuvwxyz").replaceAll("\\[0-9\\]", "0123456789");

            // if (characters.contains(NEGATOR)) {
            // characters.replace(NEGATOR, "");
            // for (int i = 0; i < 256; i++)
            // this.characters.add((char) i);
            // for (int i = 0; i < characters.length(); i++)
            // this.characters.remove(characters.charAt(i));
            // } else {
            for (int i = 0; i < characters.length(); i++)
                this.characters.add(characters.charAt(i));
            // }
        }

        public int compareTo(Streak other) {
            int comparison = ((Integer) (this.characters.size())).compareTo(other.characters.size());
            if (comparison == 0)
                comparison = ((Integer) this.characters.hashCode()).compareTo(other.characters.hashCode());
            return comparison;
        }

        @Override
        public int getPriority() {
            return 20;
        }
    }

    public class Kept extends Definition implements Comparable<Kept> {
        public final String opener, closer;

        public Kept(String title, String opener, String closer) {
            super(title);
            this.closer = closer;
            this.opener = opener;
        }

        public int compareTo(Kept other) {
            int comparison = -((Integer) this.opener.length()).compareTo(other.opener.length());
            if (comparison == 0)
                comparison = this.opener.compareTo(other.opener);
            return comparison;
        }

        @Override
        public int getPriority() {
            return 10;
        }
    }
}