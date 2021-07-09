package jile.math.expression;

// TODO is Equation a Relation?
public class Equation {

    private final TreeExpression[] sides;

    public Equation(TreeExpression[] sides) {
        this.sides = sides;
    }

    public TreeExpression[] getSides() {
        return sides;
    }
}
