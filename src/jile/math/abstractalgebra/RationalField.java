package jile.math.abstractalgebra;

import java.nio.file.Path;

import jile.math.expression.ArrayExpressionParser;
import jile.math.numbers.Rational;
import jile.math.numbers.LongRational;
import jile.math.operations.Addition;
import jile.math.operations.AdditiveInversion;
import jile.math.operations.Multiplication;
import jile.math.operations.MultiplicativeInversion;
import jile.math.settheory.InfiniteUniversalSet;
import jile.common.Convertor;
import jile.common.Random;
import jile.common.Singleton;

public class RationalField extends BaseField<Rational> implements InfiniteUniversalSet<Rational>, Singleton {

    private static RationalField singleton;

    public static RationalField singleton() {
        if (singleton == null) {
            singleton = new RationalField();
        }
        return singleton;
    }

    public static void main(String[] args) throws Exception {
        Path p = Path.of("src/res/evaluator/72-10.txt");
        System.out.println(
                new ArrayExpressionParser<Rational>(RationalField.singleton(), new Convertor<String, Rational>() {
                    @Override
                    public Rational convert(String string) {
                        return new LongRational(string);
                    }
                }, new Convertor<Rational, String>() {
                    @Override
                    public String convert(Rational element) {
                        return element.toString();
                    }
                }).evaluate(p));
    }

    private RationalField() {
        super(new LongRational(0), new AdditiveInversion<Rational>() {
            @Override
            public Rational operate(Rational x) {
                return x.negate();
            }
        }, new Addition<Rational>() {
            @Override
            public Rational operate(Rational x, Rational y) {
                return x.add(y);
            }
        }, new LongRational(1), new MultiplicativeInversion<Rational>() {
            @Override
            public Rational operate(Rational x) {
                return x.reciprocal();
            }
        }, new Multiplication<Rational>() {
            @Override
            public Rational operate(Rational x, Rational y) {
                return x.multiply(y);
            }
        });
    }

    @Override
    public Rational choose() {
        return new LongRational(Random.singleton().nextInt(10000), Random.singleton().nextInt(10000));
    }
}
