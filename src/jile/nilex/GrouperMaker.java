package jile.nilex;

import java.util.*;

import jile.nilex.IndividualTokenType.IndividualToken;
import jile.common.*;
import jile.vis.Viewer;

public class GrouperMaker extends Language {

    private static GrouperMaker singleton;

    public static GrouperMaker singleton() {
        if (singleton == null) {
            singleton = new GrouperMaker();
        }
        return singleton;
    }

    private Formalizer formalizer;

    private GrouperMaker() {
        super("Grouper Maker", "ncx", new Multiprocessor());
        formalizer = new Formalizer();
        processor.extend(formalizer.processor);
    }

    public static void main(String[] args) {
        Viewer.singleton().show(singleton().formalizer.treeMaker.makeFrom("turtle-graphics-language").visualize());
    }

    // public static final Port<Grouper> port = new Port<Grouper>();

    public final Maker<DefaultGrouper> maker = new Maker<DefaultGrouper>() {

        public DefaultGrouper make(Code code) {

            return new DefaultGrouper(formalizer.maker.make(code));

            // System.out.println(language.name + ":");
            // System.out.println("\t<+ " + key);

            // port.write(code, grouper);

            // return grouper;
        }
    };

    abstract public class Definition extends Settings {
        public Definition(String name) {
            super(name);
        }

        abstract public int getPriority();
    }

    public class Formalizer extends SettingsFormalizer implements Singleton {

        public final Maker<Map<Definition, FormalSettings>> maker = new Maker<Map<Definition, FormalSettings>>() {
            @Override
            @SuppressWarnings("unchecked")
            public Map<Definition, FormalSettings> make(Code code) {
                return (Map<Definition, FormalSettings>) Formalizer.super.mapMaker.make(code);
            }
        };

        private Formalizer() {
            super("Grouper Maker Formalizer", "ncx");
            ((Multiprocessor) processor).seal();
        }

        @Override
        public FormalizationBlueprint findType(String type) {
            switch (type) {
                case "streak":
                    return blueprintOfStreak;
                case "pattern":
                    return blueprintOfPattern;
                case "multitude":
                    return blueprintOfMultitude;
                case "rpattern":
                    return blueprintOfRelativePattern;
                case "metadata":
                    return blueprintOfMetadata;
                default:
                    // return super.findType(type);
                    return null;
            }
        }

        @Override
        public Settings wrap(FormalSettings src) {
            switch (src.src.type.data) {
                case "streak":
                    return new Streak(src);
                case "pattern":
                    return new Pattern(src);
                case "multitude":
                    return new Multitude(src);
                case "rpattern":
                    return new RelativePattern(src);
                case "metadata":
                    return new Metadata(src);
                default:
                    return null;
            }
        }

        private final FormalizationBlueprint blueprintOfStreak = new FormalizationBlueprint("streak") {
            @Override
            public void defineBlueprint() {
                addRange("length");
                add("consists-of"); // 'characters'
                add("can-start-with", DEFAULT_VALUE_STRING_SINGLE_QUOTES); // 'characters'
                add("can-end-with", DEFAULT_VALUE_STRING_SINGLE_QUOTES); // 'characters'
                add("cannot-start-with", DEFAULT_VALUE_STRING_SINGLE_QUOTES); // 'characters'
                add("cannot-end-with", DEFAULT_VALUE_STRING_SINGLE_QUOTES); // 'characters'
                addBoolean("ignore", false);
            }
        };

        public class Streak extends Definition {

            private final Set<Character> sta, mid, end;

            public final Range.Integer length;

            public final Boolean ignore;

            public Streak(FormalSettings src) {
                super(src.name);

                length = src.getRange("length");

                // extract sta, mid, and end
                char[] allCan = src.getSoleString("consists-of", true).toCharArray();
                char[] staCan = src.getSoleString("can-start-with", true).toCharArray();
                char[] endCan = src.getSoleString("can-end-with", true).toCharArray();
                char[] staCnt = src.getSoleString("cannot-start-with", true).toCharArray();
                char[] endCnt = src.getSoleString("cannot-end-with", true).toCharArray();

                sta = new HashSet<Character>();
                mid = new HashSet<Character>();
                end = new HashSet<Character>();

                for (int i = 0; i < allCan.length; i++) {
                    Character c = allCan[i];
                    sta.add(c);
                    mid.add(c);
                    end.add(c);
                }

                for (int i = 0; i < staCan.length; i++)
                    sta.add(staCan[i]);
                for (int i = 0; i < staCnt.length; i++)
                    sta.remove(staCnt[i]);

                for (int i = 0; i < endCan.length; i++)
                    end.add(endCan[i]);
                for (int i = 0; i < endCnt.length; i++)
                    end.remove(endCnt[i]);

                ignore = src.getBoolean("ignore");

            }

            public String getMid() {
                String string = "";
                for (Character c : mid)
                    string += c;
                return string;
            }

            @Override
            public int getPriority() {
                return 10;
            }

            public FormalizationBlueprint getBlueprint() {
                return blueprintOfStreak;
            }
        }

        private final FormalizationBlueprint blueprintOfPattern = new FormalizationBlueprint("pattern") {
            @Override
            public void defineBlueprint() {
                addBoolean("precedence", false);
                add("pattern", Length.ONE_OR_MORE);
            }
        };

        public class Pattern extends Definition {

            public final Boolean precedence;

            public final String[] pattern;
            public final boolean[] isVerbatim;

            public Pattern(FormalSettings src) {
                super(src.name);

                precedence = src.getBoolean("precedence");

                int length = src.getActualLengthOf("pattern");

                pattern = new String[length];
                isVerbatim = new boolean[length];

                for (int i = 0; i < length; i++) {
                    IndividualToken token = src.getTokenAt("pattern", i);
                    pattern[i] = token.data;
                    isVerbatim[i] = token.getType() instanceof DefaultTokenizer.Kept;
                }
            }

            @Override
            public int getPriority() {
                return 30;
            }
        }

        private final FormalizationBlueprint blueprintOfMultitude = new FormalizationBlueprint("multitude") {

            @Override
            public void defineBlueprint() {

                // number of elements/cells
                addRange("count");

                // cell boundary verbatim
                add("separator", DEFAULT_VALUE_NONE); // ','

                // is this multitude defining a Kept or a Group?
                addBoolean("opaque", false);

                // opener verbatim
                add("starts-with");

                // closer verbatim
                add("ends-with");

                // whether to ignore this or not
                addBoolean("ignore", false);
            }
        };

        public class Multitude extends Definition {

            public final Range.Integer count;

            public final Boolean opaque;

            public final String separator, opener, closer;

            public final Boolean ignore;

            public Multitude(FormalSettings src) {

                super(src.name);

                count = src.getRange("count");

                opaque = src.getBoolean("opaque");

                separator = src.getSoleString("separator", true);

                opener = src.getSoleString("starts-with", true);

                closer = src.getSoleString("ends-with", true);

                ignore = src.getBoolean("ignore");

            }

            @Override
            public int getPriority() {
                return 20;
            }
        }

        private final FormalizationBlueprint blueprintOfRelativePattern = new FormalizationBlueprint("rpattern") {
            @Override
            public void defineBlueprint() {
                nameMatters = false;
                // TODO inherit from 'blueprintOfPattern';
            }
        };

        public class RelativePattern extends Definition {
            public RelativePattern(FormalSettings src) {
                super(src.name);
            }

            @Override
            public int getPriority() {
                return 40;
            }
        }

        private final FormalizationBlueprint blueprintOfMetadata = new FormalizationBlueprint("metadata") {
            @Override
            public void defineBlueprint() {
                nameMatters = false;
                add("ext", Length.ONE_OR_MORE);
                add("root");
                // add("path");
            }
        };

        public class Metadata extends Definition {

            public final String[] exts;

            public final String root;

            public Metadata(FormalSettings src) {
                super(src.name);

                // exts
                var exts = src.getTokens("ext");
                this.exts = new String[exts.length];
                for (int i = 0; i < exts.length; i++)
                    this.exts[i] = exts[i].data;

                // root
                this.root = src.getSoleString("root", false);
            }

            @Override
            public int getPriority() {
                return 100;
            }
        }
    }
}