package code.node.stmt;

import code.node.ast.ExpressionNode;
import code.tokens.Token;

import java.util.List;

public class ElseNode extends StatementNode{

    public final Token token;
    public final List<StatementNode> body;

    public ElseNode(Token token, List<StatementNode> body) {
        this.token = token;
        this.body = body;
    }
}
