package jile.math.abstractalgebra;

import java.nio.file.Path;

import jile.math.expression.ArrayExpressionParser;
import jile.math.numbers.Complex;
import jile.math.operations.Addition;
import jile.math.operations.AdditiveInversion;
import jile.math.operations.Multiplication;
import jile.math.operations.MultiplicativeInversion;
import jile.math.settheory.InfiniteUniversalSet;
import jile.common.Convertor;
import jile.common.Random;
import jile.common.Singleton;

public class ComplexField extends BaseField<Complex> implements InfiniteUniversalSet<Complex>, Singleton {

    private static ComplexField singleton;

    public static ComplexField singleton() {
        if (singleton == null) {
            singleton = new ComplexField();
        }
        return singleton;
    }

    public static void main(String[] args) throws Exception {
        Path p = Path.of("src/res/evaluator/72-10.txt");
        System.out
                .println(new ArrayExpressionParser<Complex>(ComplexField.singleton(), new Convertor<String, Complex>() {
                    @Override
                    public Complex convert(String string) {
                        return new Complex(string);
                    }
                }, new Convertor<Complex, String>() {
                    @Override
                    public String convert(Complex element) {
                        return element.toString();
                    }
                }).evaluate(p));
    }

    private ComplexField() {
        super(new Complex(0), new AdditiveInversion<Complex>() {
            @Override
            public Complex operate(Complex x) {
                return x.negate();
            }
        }, new Addition<Complex>() {
            @Override
            public Complex operate(Complex x, Complex y) {
                return x.add(y);
            }
        }, new Complex(1), new MultiplicativeInversion<Complex>() {
            @Override
            public Complex operate(Complex x) {
                return x.reciprocal();
            }
        }, new Multiplication<Complex>() {
            @Override
            public Complex operate(Complex x, Complex y) {
                return x.multiply(y);
            }
        });
    }

    @Override
    public Complex choose() {
        return new Complex(Random.singleton().nextInt(10000), Random.singleton().nextInt(10000));
    }
}
