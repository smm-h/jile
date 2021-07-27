package jile.math.abstractalgebra;

import java.nio.file.Path;

import jile.math.expression.ArrayExpressionParser;
import jile.math.operations.Addition;
import jile.math.operations.AdditiveInversion;
import jile.math.operations.Multiplication;
import jile.math.operations.MultiplicativeInversion;
import jile.math.settheory.InfiniteUniversalSet;
import jile.common.Convertor;
import jile.common.Random;
import jile.common.Singleton;

public class RealField extends BaseField<Double> implements InfiniteUniversalSet<Double>, Singleton {

    private static RealField singleton;

    public static RealField singleton() {
        if (singleton == null) {
            singleton = new RealField();
        }
        return singleton;
    }

    public static void main(String[] args) throws Exception {
        Path p = Path.of("src/res/evaluator/72-10.txt");
        System.out.println(new ArrayExpressionParser<Double>(RealField.singleton(), new Convertor<String, Double>() {
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

    private RealField() {
        super(0.0, new AdditiveInversion<Double>() {
            @Override
            public Double operate(Double x) {
                return -x;
            }
        }, new Addition<Double>() {
            @Override
            public Double operate(Double x, Double y) {
                return x + y;
            }
        }, 1.0, new MultiplicativeInversion<Double>() {
            @Override
            public Double operate(Double x) {
                return 1 / x;
            }
        }, new Multiplication<Double>() {
            @Override
            public Double operate(Double x, Double y) {
                return x * y;
            }
        });
    }

    @Override
    public Double choose() {
        return Math.sqrt(Random.singleton().nextInt() / 100.0);
    }
}
