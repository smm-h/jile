package jile.math.expression;

import java.awt.Graphics2D;
import java.awt.Point;

import jile.common.Tree;
import jile.vis.View;

public class ExpressionView extends View {

    private final Tree<Expression> expression;

    public ExpressionView(Tree<Expression> expression) {
        super(View.Mode.STILL);
        this.expression = expression;
    }

    @Override
    public String getIdentity() {
        return "Expression: " + expression;
    }

    @Override
    public <Model> Element<Model> findElementAt(Point point) {
        return null;
    }

    @Override
    public void draw(Graphics2D g) {
        // TODO Auto-generated method stub

    }

    @Override
    public void _refresh() {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return expression.toString();
    }

}
