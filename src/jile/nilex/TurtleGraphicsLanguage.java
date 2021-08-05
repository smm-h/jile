package jile.nilex;

import java.util.*;

import jile.nilex.Code.Process;
import jile.nilex.IndividualTokenType.IndividualToken;
import jile.nilex.CollectiveTokenType.CollectiveToken;
import jile.nilex.TurtleGraphics.Turtle;
import jile.nilex.processors.Multiprocessor;
import jile.common.*;
import jile.vis.*;

public class TurtleGraphicsLanguage extends Language implements Singleton, Linter {

    private static TurtleGraphicsLanguage singleton;

    public static TurtleGraphicsLanguage singleton() {
        if (singleton == null) {
            singleton = new TurtleGraphicsLanguage();
        }
        return singleton;
    }

    public static void main(String[] args) {
        Viewer.singleton().show(singleton().maker.makeFrom("b"));
    }

    private TurtleGraphicsLanguage() {

        super("Turtle Graphics Language", "tgx", new Multiprocessor());

        // reads: code
        // writes: Grouper.grouped
        processor.extend(GrouperMaker.singleton().maker.makeFrom("turtle-graphics-language"));

        // reads: Grouper.grouped
        // writes: TurtleGraphicsLanguage:recorded
        processor.extend(new Parser(Keywords.values()));

        // reads: TurtleGraphicsLanguage:recorded
        // makes: TurtleGraphics
        setMainMaker(maker);
    }

    private static final Port<ParrotGraphics> recorded = new Port<ParrotGraphics>("TurtleGraphicsLanguage:recorded");

    public final Maker<Theatre> maker = new Maker<Theatre>() {
        @Override
        public Theatre make(Code code) {
            Theatre theatre = Objects.requireNonNull(recorded.read(code)).visualize();
            theatre.keepLooping.set(false);
            // theatre.pace.set(theatre.pace.get() / 8);
            theatre.pace.set(1L);
            return theatre;
        }
    };

    private final Map<Character, Rule> rules = new HashMap<Character, Rule>();

    private class Rule {

        final char[] pattern;
        final CollectiveToken group;

        public Rule(char[] pattern, CollectiveToken group) {
            this.pattern = pattern;
            this.group = group;
        }
    }

    private class Parser extends KeywordParser {

        public Parser(ParsibleKeywords[] values) {
            super(values);
        }

        private Set<Walker> walkers = new HashSet<Walker>();
        private static final boolean parallel = true;

        @Override
        public void process(Code code) {
            // reads: Grouper.grouped
            // writes: port

            var process = code.new Process("parsing");

            var root = (CollectiveToken) Grouper.grouped.read(code);

            nameGroup("root", root);

            Walker walker = new Walker(process, root);

            try {
                while (true) {
                    Thread.sleep(100);
                    if (walkers.isEmpty()) {
                        if (process.finish()) {
                            recorded.write(code, walker.tg.getParrot());
                        } else {
                            System.out.println("Fatal mishaps occurred");
                        }
                        break;
                    } else {
                        walkers.iterator().next().close();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                process.finish();
            }
        }

        private class Walker extends KeywordWalker {

            private final Walker cause;
            private final Map<String, Float> variables = new HashMap<String, Float>();
            private final TurtleGraphics tg;
            private final Turtle agent;

            public Walker(Process process, CollectiveToken root) {
                this(process, root, null);
            }

            public Walker(Walker cause, CollectiveToken root) {
                this(cause.getProcess(), root, cause);
            }

            private Walker(Walker cause, IndividualToken token, char[] symbols) {
                super(cause.getProcess());

                Objects.requireNonNull(symbols);

                this.cause = cause;
                this.tg = cause == null ? new TurtleGraphics() : cause.tg;
                this.agent = cause == null ? tg.new Turtle() : tg.new Turtle(cause.agent);

                walkers.add(this);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        callSymbols(token, symbols);
                        close();
                    }
                };

                if (parallel)
                    thread.start();
                else
                    thread.run();
            }

            private Walker(Process process, CollectiveToken root, Walker cause) {

                super(process);

                Objects.requireNonNull(root);

                this.cause = cause;
                this.tg = cause == null ? new TurtleGraphics() : cause.tg;
                this.agent = cause == null ? tg.new Turtle() : tg.new Turtle(cause.agent);

                walkers.add(this);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        enter(root);
                        parse();
                        exit();
                        close();
                    }
                };

                if (parallel)
                    thread.start();
                else
                    thread.run();
            }

            public void close() {
                walkers.remove(this);
            }

            @Override
            public Keywords next() {
                return Keywords.valueOf(getNextKeyword());
            }

            private void parse() {
                loop: while (true) {
                    switch (next()) {
                        case NOT_A_KEYWORD:
                            break;
                        case NO_MORE_TOKENS:
                            break loop;
                        case DRAW: {
                            Float v = parseValue();
                            if (v != null)
                                agent.go(v, true);
                            break;
                        }
                        case MOVE: {
                            Float v = parseValue();
                            if (v != null)
                                agent.go(v, false);
                            break;
                        }
                        case TURN: {
                            Float v = parseValue();
                            if (v != null)
                                agent.turn(v);
                            break;
                        }
                        case SCALE: {
                            Float v = parseValue();
                            if (v != null)
                                agent.setScale(v);
                            break;
                        }
                        case SET: {
                            String identifier = parseIdentifier();
                            Float v = parseValue();
                            if (v != null)
                                set(identifier, v);
                            break;
                        }
                        case INCREMENT: {
                            String v = parseIdentifier(resolver);
                            if (v != null)
                                set(v, get(v) + 1);
                            break;
                        }
                        case DECREMENT: {
                            String v = parseIdentifier(resolver);
                            if (v != null)
                                set(v, get(v) - 1);
                            break;
                        }
                        case REPEAT: {
                            Float v = parseValue();
                            if (v == null)
                                v = 0f;
                            var block = parseGroup("curly_braces");
                            if (block != null) {
                                enter(block);
                                for (int i = 0; i < v; i++) {
                                    parse();
                                    reenter();
                                }
                                exit();
                            }
                            break;
                        }
                        case DEFINE: {
                            parseGroup("curly_braces", parseIdentifier());
                            break;
                        }
                        case CALL: {
                            if (agent.getScale() >= 0.1f) {
                                // if (getDepth() < 5) {
                                var group = findGroup(parseGroupIdentifier());
                                if (group != null) {
                                    enter(group);
                                    parse();
                                    exit();
                                }
                            }
                            break;
                        }
                        case RULE: {
                            char symbol = parseIdentifier().charAt(0);

                            // new Resolver() {
                            // @Override
                            // public boolean canBeResolved(String identifier) {
                            // return rules.containsKey(identifier.charAt(0));
                            // }
                            // }

                            if (peekMustBe("pattern", "single_quotes")) {
                                char[] pattern = poll().getData().toCharArray();
                                var block1 = parseGroup("curly_braces");
                                if (block1 != null) {
                                    rules.put(symbol, new Rule(pattern, block1));
                                }
                            }
                            break;
                        }
                        case EXPAND: {
                            Float v = parseValue();
                            if (v == null)
                                v = 0f;
                            if (peekMustBe("pattern", "single_quotes")) {
                                IndividualToken pattern = (IndividualToken) poll();
                                char[] generation = pattern.getData().toCharArray();
                                for (int i = 0; i < v; i++) {
                                    generation = expand(generation);
                                }
                                callSymbols(pattern, generation);
                            }
                            break;
                        }
                        case DETOUR: {
                            var block = parseGroup("curly_braces");
                            if (block != null)
                                new Walker(this, block);
                            break;
                        }
                    }
                }
            }

            private char[] expand(char[] input) {
                int outputLength = 0;
                for (char key : input) {
                    if (rules.containsKey(key)) {
                        outputLength += rules.get(key).pattern.length;
                    } else {
                        outputLength += 1;
                    }
                }
                char[] output = new char[outputLength];
                int pointer = 0;
                for (char key : input) {
                    if (rules.containsKey(key)) {
                        var pattern = rules.get(key).pattern;
                        for (int index = 0; index < pattern.length; index++) {
                            output[pointer++] = pattern[index];
                        }
                    } else {
                        output[pointer++] = key;
                    }
                }
                return output;
            }

            private void callSymbols(IndividualToken token, char[] symbols) {
                int index = 0;
                int size = symbols.length;
                while (index < size) {
                    char key = symbols[index++];
                    if (rules.containsKey(key)) {
                        var group = rules.get(key).group;
                        if (group == null) {
                            getProcess().issue(new RuleNotFound(token, key));
                        } else {
                            enter(group);
                            parse();
                            exit();
                        }
                    } else {
                        switch (key) {
                            case '+':
                                agent.turn(+get("angle"));
                                break;
                            case '-':
                                agent.turn(-get("angle"));
                                break;
                            case '[':
                                // turtles.push(tg.new Turtle(agent));
                                int start = index;
                                int end = index;
                                while (index < size) {
                                    if (symbols[index++] == ']') {
                                        end = index;
                                        break;
                                    }
                                }
                                new Walker(cause, token, Arrays.copyOfRange(symbols, start, end));
                                break;
                            // case ']':
                            // // turtles.pop();
                            // break;
                            default:
                                System.out.println("Unknown symbol: " + key);
                                break;
                        }
                    }
                }
            }

            private void set(String v, float value) {
                if (v != null)
                    variables.put(v, value);
            }

            private final Resolver resolver = new Resolver() {
                @Override
                public boolean canBeResolved(String identifier) {
                    return get(identifier) != null;
                }
            };

            private Float get(String v) {
                Walker w = this;
                while (w != null) {
                    if (w.variables.containsKey(v)) {
                        return w.variables.get(v);
                    } else {
                        w = w.cause;
                    }
                }
                return null;
            }

            /** if this returns null, a mishap must be issued. */
            private Float parseValue() {
                if (peek("operator").is("verbatim <+>", "verbatim <->", "verbatim <*>", "verbatim </>")) {
                    String op = poll().getData();
                    Float lhs = parseValue();
                    Float rhs = parseValue();
                    if (lhs != null && rhs != null) {
                        switch (op) {
                            case "+":
                                return lhs + rhs;
                            case "-":
                                return lhs - rhs;
                            case "*":
                                return lhs * rhs;
                            case "/":
                                return lhs / rhs;
                            default:
                                // impossible to happen
                                return null;
                        }
                    } else {
                        // mishap already issued
                        return null;
                    }
                } else if (peek("value").is("identifier")) {
                    return get(parseIdentifier(resolver));
                } else {
                    // signed is impossible because the sign would be interpreted as
                    // a prefix operator
                    return parseUnsignedFloat();
                }
            }
        }
    }

    private enum Keywords implements KeywordParser.ParsibleKeywords {

        NO_MORE_TOKENS, NOT_A_KEYWORD,

        TURN, DRAW, MOVE, SCALE,

        SET, INCREMENT, DECREMENT,

        REPEAT, DEFINE, CALL,

        EXPAND, RULE, DETOUR,
    }

    abstract public class LindenmayerMishap extends Mishap {
        public LindenmayerMishap(IndividualToken token, boolean fatal) {
            super(token, fatal);
        }
    }

    public class RuleNotFound extends LindenmayerMishap {

        private final char symbol;

        public RuleNotFound(IndividualToken token, char symbol) {
            super(token, true);
            this.symbol = symbol;
        }

        @Override
        public String getReport() {
            return "Rule not found: `" + symbol + "`";
        }
    }
}