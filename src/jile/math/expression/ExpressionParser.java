package jile.math.expression;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import jile.math.abstractalgebra.RealField;
import jile.math.abstractalgebra.Ring;
import jile.common.Convertor;

/**
 * An {@link ExpressionParser} takes a file or a string, and produces an
 * {@link Expression}.
 * 
 * @see ArrayExpressionParser
 * @see StackExpressionParser
 */
abstract public class ExpressionParser<T> {

    private final Ring<T> ring;
    private final Convertor<String, T> decoder;
    private final Convertor<T, String> encoder;

    public ExpressionParser(Ring<T> ring, Convertor<String, T> decoder, Convertor<T, String> encoder) {
        this.ring = ring;
        this.decoder = decoder;
        this.encoder = encoder;
    }

    public Ring<T> getRing() {
        return ring;
    }

    public T decode(String source) {
        return decoder.convert(source);
    }

    public String encode(T source) {
        return encoder.convert(source);
    }

    /**
     * Read the expression (last line) and the variables (the rest) from file.
     * 
     * @throws IOException
     */
    public String evaluate(Path path) throws IOException {
        String[] lines = Files.readString(path).split("\n");
        for (String line : lines) {
            line = line.strip();
            if (line.contains("=")) {
                String[] keyValuePair = line.split("=");
                assert keyValuePair.length == 2;
                assert keyValuePair[0].length() == 1;
                char key = keyValuePair[0].charAt(0);
                String value = keyValuePair[1];
                setValue(key, decode(value));
                value = "";
            } else if (line.isEmpty()) {
                // ignore empty lines
            } else {
                return encode(evaluate(line));
            }
        }
        return null;
    }

    /**
     * For example: {@code evaluate("a+b", "a=5\nb=6");} will return "11".
     */
    public String evaluate(String expression, String variables) {
        int i = 0;
        char key = 0;
        String value = "";
        boolean readingDigits = false;
        char[] v = (variables + key).toCharArray();
        while (i < v.length) {
            if (readingDigits) {
                if (isDigit(v[i])) {
                    value += v[i];
                } else {
                    readingDigits = false;
                    setValue(key, decode(value));
                    value = "";
                }
            } else {
                if (isLetter(v[i])) {
                    key = v[i];
                    readingDigits = true;
                    i++;
                } else {
                    // System.out.println("Error: unknown character: " + v[i]);
                }
            }
            i++;
        }
        return encode(evaluate(expression));
    }

    private boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    private boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    public static void main(String[] args) throws Exception {
        RealField.main(null);
    }

    abstract protected void setValue(char key, T value);

    abstract protected T evaluate(String expression);
}