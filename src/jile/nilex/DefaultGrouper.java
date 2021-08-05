package jile.nilex;

import java.util.*;

import jile.nilex.CollectiveTokenType.CollectiveToken;
import jile.nilex.IndividualTokenType.IndividualToken;
import jile.nilex.GrouperMaker.Definition;
import jile.nilex.GrouperMaker.Formalizer.*;
import jile.nilex.SettingsFormalizer.FormalSettings;
import jile.nilex.processors.SingleProcessor;
// import jile.common.Convertor;
// import jile.common.TraversibleTree;
import jile.vis.TreeView;

public class DefaultGrouper extends SingleProcessor implements Grouper {

    private final Map<String, GroupType> groupTypes = new HashMap<String, GroupType>();

    private GroupType rootGroupType = null;

    private DefaultTokenizer tokenizer = new DefaultTokenizer();

    private final Map<Definition, FormalSettings> sources;

    private FormalSettings findSourceForDefinition(Definition definition) {
        return sources.get(definition);
    }

    public DefaultGrouper(Map<Definition, FormalSettings> sources) {

        this.sources = sources;

        for (Definition key : sources.keySet())
            schedule(key);

        defineAsScheduled();
    }

    @Override
    public void process(Code code) {

        tokenizer.process(code);

        var planning = code.new Process("planning-to-group");

        List<IndividualToken> tokens = DefaultTokenizer.tokenized.read(code);

        if (groupTypes.isEmpty())
            planning.issue(new NoGroupTypeDefined(null));

        if (rootGroupType == null)
            planning.issue(new NoRootGroupType(null));

        Map<Integer, Deepening> deepeningDuties = new HashMap<Integer, Deepening>();

        Map<Integer, CollectiveToken> groupingDuties = new HashMap<Integer, CollectiveToken>();

        Stack<Integer> opened = new Stack<Integer>();

        outer: for (GroupType type : groupTypes.values()) {
            int separatorCount = 0;
            Stack<IndividualToken> openerStack = new Stack<IndividualToken>();
            DefaultTokenizer.Verbatim separator = null;
            for (int index = 0; index < tokens.size(); index++) {
                IndividualToken token = tokens.get(index);
                if (token.getType() instanceof DefaultTokenizer.Verbatim) {
                    DefaultTokenizer.Verbatim verbatim = (DefaultTokenizer.Verbatim) token.getType();
                    if (verbatim.equals(type.opener)) {
                        openerStack.push(token);
                        opened.push(index);
                    } else if (verbatim.equals(type.closer)) {
                        int openedIndex = opened.pop();
                        deepeningDuties.put(openedIndex, Deepening.OPEN);
                        // deepeningDuties.put(openedIndex, Deepening.OPEN_AND_SPLIT);
                        deepeningDuties.put(index, Deepening.CLOSE);
                        // deepeningDuties.put(index, Deepening.STITCH_AND_CLOSE);
                        groupingDuties.put(openedIndex, type.new CollectiveToken(openerStack.pop(), token));
                    } else if (verbatim.equals(type.separator)) {
                        if (separatorCount == 0) {
                            separator = verbatim;
                        } else {
                            if (!verbatim.equals(separator)) {
                                planning.issue(new TwoDifferentSeparators(token, groupingDuties.get(opened.peek()),
                                        verbatim, separator));
                                break outer;
                            }
                        }
                        // deepeningDuties.put(index, Deepening.STITCH_AND_SPLIT);
                        separatorCount++;
                        // TODO apply separation constraints
                    }
                }
            }
            if (!opened.isEmpty()) {
                planning.issue(new Unbalanced(tokens.get(opened.peek())));
            }
        }

        // TokenType cellType; TODO

        if (planning.finish()) {

            var grouping = code.new Process("grouping");

            // System.out.println(groupingDuties);
            // System.out.println(deepeningDuties);

            // LinkedTree<CollectiveToken> tree = new LinkedTree<CollectiveToken>();
            // tree.addAndGoTo(rootGroupType.new CollectiveToken(0, code.getSize() - 1));

            Stack<CollectiveToken> roots = new Stack<CollectiveToken>();
            roots.push(rootGroupType.new CollectiveToken());

            // LinkedTree<Token> vtree = new LinkedTree<Token>();
            // vtree.addAndGoTo(new VirtualToken("<ROOT>"));

            Stack<CollectiveToken> groups = new Stack<CollectiveToken>();

            for (int index = 0; index < tokens.size(); index++) {
                // System.out.println(index);
                IndividualToken token = tokens.get(index);
                if (groupingDuties.containsKey(index)) {
                    CollectiveToken duty = groupingDuties.get(index);
                    if (duty == null) {
                        groups.pop();
                    } else {
                        groups.push(duty);
                    }
                }
                if (deepeningDuties.containsKey(index)) {
                    switch (deepeningDuties.get(index)) {
                        case OPEN:
                            // tree.addAndGoTo(groups.peek());
                            roots.peek().add(groups.peek());
                            roots.push(groups.peek());
                            break;
                        case CLOSE:
                            // tree.goBack();
                            roots.pop();
                            break;
                        // case OPEN_AND_SPLIT:
                        // groups.peek().split();
                        // tree.addAndGoTo(groups.peek());
                        // break;
                        // case STITCH_AND_SPLIT:
                        // groups.peek().split();
                        // break;
                        // case STITCH_AND_CLOSE:
                        // tree.goBack();
                        // break;
                    }
                } else {
                    roots.peek().add(token);
                    // tree.getPointer().add(token);
                }
            }

            if (grouping.finish()) {

                var root = Objects.requireNonNull(roots.pop());

                DefaultGrouper.grouped.write(code, root);

                var t = root.toTree();

                TreeView.port.write(code, t);
                // TreeView.port.write(code, ((TraversibleTree<Token>) t).convert(new
                // Convertor<Token, String>() {

                // @Override
                // public String convert(Token source) {
                // return source.toString();
                // }

                // }));
            }
        }
    }

    public class GroupType extends CollectiveTokenType implements Comparable<GroupType> {

        public final DefaultTokenizer.Verbatim opener, closer, separator;

        public final IndividualTokenType cellType;

        public GroupType(String name, DefaultTokenizer.Verbatim opener, DefaultTokenizer.Verbatim closer,
                DefaultTokenizer.Verbatim separator, IndividualTokenType cellType) {
            super("group_type <" + name + ">", opener.data + "..." + closer.data);
            this.opener = opener;
            this.closer = closer;
            this.separator = separator;
            this.cellType = cellType;
        }

        @Override
        public int compareTo(GroupType other) {
            int comparison = -((Integer) this.opener.data.length()).compareTo(other.opener.data.length());
            if (comparison == 0)
                comparison = this.opener.compareTo(other.opener);
            return comparison;
        }
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
            // System.out.println("::: " + definition.getPriority() + "\t" +
            // definition.src.name);
            define(definition);
        }
    }

    public void define(Definition definition) {

        if (definition instanceof Metadata)
            define((Metadata) definition);

        else if (definition instanceof Streak)
            define((Streak) definition);

        else if (definition instanceof Multitude)
            define((Multitude) definition);

        else if (definition instanceof Pattern)
            define((Pattern) definition);

        else if (definition instanceof RelativePattern)
            define((RelativePattern) definition);

        else {
            // no more define-ables
        }
    }

    public void define(Metadata d) {
        rootGroupType = Objects.requireNonNull(groupTypes.get(d.root));
        System.out.println("Root group type was set");
        // for (int i = 0; i < m.exts.length; i++)
        // Languages.singleton().associateExtWithLanguage(m.exts[i], language);
    }

    private static final TokenizerMaker tm = TokenizerMaker.singleton();

    public void define(Streak d) {

        tokenizer.define(tm.new Streak(d.absoluteName, d.getMid()));

        if (d.ignore)
            if (!tokenizer.ignore(d.absoluteName))
                // TODO turn this into a mishap
                System.out.println("Could not ignore: " + d.absoluteName);
    }

    public void define(Multitude d) {

        if (d.opaque) {

            tokenizer.define(tm.new Kept(d.absoluteName, d.opener, d.closer));

            if (d.separator != "none")
                new MultitudeOpaqueButSeparated(findSourceForDefinition(d));

            if (d.ignore)
                if (!tokenizer.ignore(d.absoluteName))
                    // TODO turn this into a mishap
                    System.out.println("Could not ignore: " + d.absoluteName);

        } else {

            DefaultTokenizer.Verbatim opener = tokenizer.define(tm.new Verbatim(d.opener));
            DefaultTokenizer.Verbatim closer = tokenizer.define(tm.new Verbatim(d.closer));
            DefaultTokenizer.Verbatim separator = tokenizer.define(tm.new Verbatim(d.separator));
            groupTypes.put(d.absoluteName, new GroupType(d.absoluteName, opener, closer, separator, null));

            if (d.ignore)
                new MultitudeNotOpaqueButIgnored(findSourceForDefinition(d));
        }
    }

    // public final Boolean precedence;
    // public final String[] pattern;
    // public final boolean[] isVerbatim;
    public void define(Pattern d) {

        for (int i = 0; i < d.pattern.length; i++) {
            if (d.isVerbatim[i]) {
                tokenizer.define(tm.new Verbatim(d.pattern[i]));
            }
        }
    }

    public void define(RelativePattern d) {

    }

    enum Deepening {
        OPEN, CLOSE // , OPEN_AND_SPLIT, STITCH_AND_CLOSE, STITCH_AND_SPLIT
    }

    abstract public class GrouperMishap extends Mishap {

        public GrouperMishap(IndividualToken token, boolean fatal) {
            super(token, fatal);
        }
    }

    public class Unbalanced extends GrouperMishap {

        public Unbalanced(IndividualToken opener) {
            super(opener, true);
            assert opener.getType() instanceof DefaultTokenizer.Verbatim;
        }

        @Override
        public String getReport() {
            return "Opened but not closed: `" + token.getData() + "`";
        }
    }

    public class NoGroupTypeDefined extends GrouperMishap {
        public NoGroupTypeDefined(IndividualToken token) {
            super(token, true);
        }

        @Override
        public String getReport() {
            return "No group types are defined";
        }
    }

    public class NoRootGroupType extends GrouperMishap {
        public NoRootGroupType(IndividualToken token) {
            super(token, true);
        }

        @Override
        public String getReport() {
            return "Root group type is not set";
        }
    }

    public class TwoDifferentSeparators extends GrouperMishap {
        CollectiveToken group;
        DefaultTokenizer.Verbatim one, another;

        public TwoDifferentSeparators(IndividualToken token, CollectiveToken group, DefaultTokenizer.Verbatim first,
                DefaultTokenizer.Verbatim second) {
            super(token, true);
            this.group = group;
            this.one = first;
            this.another = second;
        }

        @Override
        public String getReport() {
            return "The group `" + group.toString() + "` was separated by more than one separators";
        }
    }

    public class MultitudeOpaqueButSeparated extends GrouperMishap {

        private final FormalSettings src;

        public MultitudeOpaqueButSeparated(FormalSettings src) {
            super(src.getTokenAt("separator", 0), false);
            this.src = src;
        }

        @Override
        public String getReport() {
            return "`" + src.getIdentity()
                    + "` cannot both be opaque and have a separator. Change this to `none`, or delete it.";
        }
    }

    public class MultitudeNotOpaqueButIgnored extends GrouperMishap {

        private final FormalSettings src;

        public MultitudeNotOpaqueButIgnored(FormalSettings src) {
            super(src.getTokenAt("ignore", 0), false);
            this.src = src;
        }

        @Override
        public String getReport() {
            return src.getIdentity()
                    + " cannot both be non-opaque and ignored. Change this to `false`, or delete it, or change `opaque` to `true`.";
        }
    }
}
