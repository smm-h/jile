package jile.math.expression;

// import jile.common.Tree;
import jile.vis.TextView;
import jile.vis.View;
import jile.vis.Visualizable;

public interface Expression extends Visualizable {

    public Object evaluate();

    // public Tree<Expression> express();
    public String express();

    @Override
    default View visualize() {
        // return new ExpressionView(express());
        return new TextView(express());
    }
}
