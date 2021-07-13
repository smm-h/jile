package jile.cs;

import jile.math.numbers.Integer;
import jile.math.numbers.IntegerInteger;
import jile.math.numbers.Real;
import jile.math.operations.IntegerModulo;
import jile.math.tuples.HomogeneousCouple;
import jile.math.tuples.Monuple;
import jile.common.Common;
import jile.vis.Viewer;
import jile.vis.Visualizable;

public class EuclideanGCD implements Solution<GCD, HomogeneousCouple<Integer>, Monuple<Integer>> {

    @Override
    public Monuple<Integer> solve(HomogeneousCouple<Integer> i) {
        int a = (int) i.getFirst().approximate();
        int b = (int) i.getSecond().approximate();
        Integer o = Integer.fromContents(Common.gcd(a, b));
        return new Monuple<Integer>() {
            @Override
            public Integer getFirst() {
                return o;
            }
        };
    }

    @Override
    public Visualizable getSolution(HomogeneousCouple<Integer> i) {
        Integer a = i.getFirst();
        Integer b = i.getSecond();
        if (b == Real.ZERO)
            return a;
        else
            return getSolution(b, new IntegerModulo(a, b));
    }

    public static void main(String[] args) {
        HomogeneousCouple<Integer> i = new HomogeneousCouple<Integer>() {
            public Integer getFirst() {
                return new IntegerInteger(572);
            };

            public Integer getSecond() {
                return new IntegerInteger(180);
            };
        };
        Viewer.singleton().show(new EuclideanGCD().getSolution(i).visualize());
    }

}