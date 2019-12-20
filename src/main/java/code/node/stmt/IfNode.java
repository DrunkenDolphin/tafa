package code.node.stmt;

import code.tokens.Token;
import java.util.List;
import code.node.ast.ExpressionNode;

public class IfNode extends StatementNode{

    public final Token token;
    public final ExpressionNode condition;
    public final List<StatementNode> body;
    public ElseNode elseNode;

    public IfNode(Token token, ExpressionNode condition,  List<StatementNode> body, ElseNode elseNode) {
        this.token = token;
        this.condition = condition;
        this.body = body;
        this.elseNode = elseNode;
    }

}
