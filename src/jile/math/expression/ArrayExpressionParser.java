package jile.math.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jile.math.abstractalgebra.Ring;
import jile.common.Convertor;

/**
 * An {@link ArrayExpressionParser} is an {@link ExpressionParser} that uses
 * arrays instead of stacks.
 * 
 * @see StackExpressionParser
 */
public class ArrayExpressionParser<T> extends ExpressionParser<T> {

    private final Map<Character, Integer> names;
    private final List<T> values;

    public ArrayExpressionParser(Ring<T> ring, Convertor<String, T> decoder, Convertor<T, String> encoder) {
        super(ring, decoder, encoder);
        names = new HashMap<Character, Integer>();
        values = new ArrayList<T>();
        values.add(null);
    }

    @Override
    protected void setValue(char key, T value) {
        int id = values.size();
        names.put(key, id);
        values.add(value);
        System.out.println("[" + id + "] (" + key + ") <- " + value);
    }

    @Override
    protected T evaluate(String expression) {
        char[] e = expression.toCharArray();
        int n = e.length;
        int[] u = new int[n];
        int id;
        int l_reach, r_reach, f;
        T lhs, rhs, output;

        for (int i = 1; i < n - 1; i++) {

            if (e[i] == '*' || e[i] == '/') {

                id = values.size();

                l_reach = i - 1;
                f = u[l_reach];
                lhs = values.get(f == 0 ? names.get(e[i - 1]) : f);

                if (f == 0) {
                    u[i - 1] = id;
                } else {
                    while (u[l_reach] == f) {
                        u[l_reach] = id;
                        l_reach--;
                        if (l_reach == 0)
                            break;
                    }
                }

                u[i] = id;

                r_reach = i + 1;
                f = u[r_reach];
                rhs = values.get(f == 0 ? names.get(e[i + 1]) : f);

                if (f == 0) {
                    u[i + 1] = id;
                } else {
                    while (u[r_reach] == f) {
                        u[r_reach] = id;
                        r_reach++;
                        if (r_reach == n)
                            break;
                    }
                }

                if (e[i] == '*')
                    output = getRing().multiply(lhs, rhs);
                else
                    output = getRing().divide(lhs, rhs);

                values.add(output);
                System.out.println("[" + u[i] + "] <- " + encode(output) + " <- " + lhs + " " + e[i] + " " + rhs);
            }
        }

        for (int i = 1; i < n - 1; i++) {

            if (e[i] == '+' || e[i] == '-') {

                id = values.size();

                l_reach = i - 1;
                f = u[l_reach];
                lhs = values.get(f == 0 ? names.get(e[i - 1]) : f);

                if (f == 0) {
                    u[i - 1] = id;
                } else {
                    while (u[l_reach] == f) {
                        u[l_reach] = id;
                        l_reach--;
                        if (l_reach == 0)
                            break;
                    }
                }

                u[i] = id;

                r_reach = i + 1;
                f = u[r_reach];
                rhs = values.get(f == 0 ? names.get(e[i + 1]) : f);

                if (f == 0) {
                    u[i + 1] = id;
                } else {
                    while (u[r_reach] == f) {
                        u[r_reach] = id;
                        r_reach++;
                        if (r_reach == n)
                            break;
                    }
                }

                if (e[i] == '+')
                    output = getRing().add(lhs, rhs);
                else
                    output = getRing().subtract(lhs, rhs);

                values.add(output);
                System.out.println("[" + u[i] + "] <- " + encode(output) + " <- " + lhs + " " + e[i] + " " + rhs);
            }
        }

        return values.get(values.size() - 1);
    }
}
