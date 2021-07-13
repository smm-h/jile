package jile.math.expression;

import java.nio.file.Path;
import java.util.*;

import jile.math.abstractalgebra.RealField;
import jile.math.abstractalgebra.Ring;
import jile.common.Convertor;

/**
 * A {@link StackExpressionParser} is an {@link ExpressionParser} that uses
 * {@link Stack}s.
 * 
 * @see ArrayExpressionParser
 */
public class StackExpressionParser<T> extends ExpressionParser<T> {

    private final Map<Character, T> variables;

    public StackExpressionParser(Ring<T> ring, Convertor<String, T> decoder, Convertor<T, String> encoder) {
        super(ring, decoder, encoder);
        variables = new HashMap<Character, T>();
    }

    @Override
    protected void setValue(char key, T value) {
        variables.put(key, value);
    }

    @Override
    protected T evaluate(String expression) {

        // int pushes = 0;
        // int pops = 0;

        Stack<Character> stack = new Stack<Character>();
        LinkedList<Character> temp = new LinkedList<Character>();

        char[] e = expression.toCharArray();
        int n = e.length;

        for (int i = 0; i < n; i++) {

            if (i % 2 == 0) {
                stack.push(e[i]);
            } else {
                while (precedenceOf(stack.peek()) >= precedenceOf(e[i])) {
                    temp.add(stack.pop());
                    // pushes++;
                }
                stack.push(e[i]);
                while (!temp.isEmpty()) {
                    stack.push(temp.removeLast());
                    // pops++;
                }
            }
        }

        return null;
    }

    private int precedenceOf(Character symbol) {
        return 0;
    }

    public static void main(String[] args) throws Exception {
        Path p = Path.of("src/res/evaluator/72-10.txt");
        System.out.println(new StackExpressionParser<Double>(RealField.singleton(), new Convertor<String, Double>() {
            @Override
            public Double convert(String string) {
                return Double.parseDouble(string);
            }
        }, new Convertor<Double, String>() {
            @Override
            public String convert(Double element) {
                return Double.toString(element);
            }
        }).evaluate(p));
    }
}