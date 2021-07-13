package jile.math.abstractalgebra;

import java.nio.file.Path;

import jile.math.expression.ArrayExpressionParser;
import jile.math.numbers.BigNatural;
import jile.math.operations.Addition;
import jile.math.operations.AdditiveInversion;
import jile.math.operations.Multiplication;
import jile.math.operations.MultiplicativeInversion;
import jile.math.settheory.NonNullibleInfiniteUniversalSet;
import jile.common.Common;
import jile.common.Convertor;
import jile.common.Random;
import jile.common.Singleton;

public class BigNaturalField extends BaseField<BigNatural>
        implements NonNullibleInfiniteUniversalSet<BigNatural>, Singleton {

    private static BigNaturalField singleton;

    public static BigNaturalField singleton() {
        if (singleton == null) {
            singleton = new BigNaturalField(Common.RADIX_BIN);
        }
        return singleton;
    }

    public static void main(String[] args) throws Exception {
        var b = BigNaturalField.singleton();
        var p = Path.of("src/res/evaluator/4368-" + b.radix + ".txt");
        System.out.println(new ArrayExpressionParser<BigNatural>(b, new Convertor<String, BigNatural>() {
            @Override
            public BigNatural convert(String string) {
                return new BigNatural(string, singleton().radix);
            }
        }, new Convertor<BigNatural, String>() {
            @Override
            public String convert(BigNatural element) {
                return element.toString();
            }
        }).evaluate(p));
    }

    private final byte radix;

    private BigNaturalField(byte radix) {
        super(new BigNatural("0", radix), new AdditiveInversion<BigNatural>() {
            @Override
            public BigNatural operate(BigNatural x) {
                // return x.additiveInverse();
                throw new UnsupportedOperationException("cannot subtract BigNatural");
            }
        }, new Addition<BigNatural>() {
            @Override
            public BigNatural operate(BigNatural x, BigNatural y) {
                return x.add(y);
            }
        }, new BigNatural("1", radix), new MultiplicativeInversion<BigNatural>() {
            @Override
            public BigNatural operate(BigNatural x) {
                // return x.multiplicativeInverse();
                throw new UnsupportedOperationException("cannot divide BigNatural");
            }
        }, new Multiplication<BigNatural>() {
            @Override
            public BigNatural operate(BigNatural x, BigNatural y) {
                return x.multiply(y);
            }
        });
        this.radix = radix;
    }

    @Override
    public BigNatural choose() {
        return new BigNatural(Random.singleton().nextLong(), radix);
    }
}
