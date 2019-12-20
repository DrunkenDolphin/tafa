package  code;

import java.util.*;
import java.util.List;
import code.node.ast.*;
import code.node.stmt.*;
import java.util.Arrays;
import code.tokens.Token;
import code.tokens.TokenType;

import static com.sun.tools.doclint.Entity.and;

public class Parser {

    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private void error(String message) {
        if (pos < tokens.size()) {
            Token t = tokens.get(pos);
            throw new RuntimeException(message + " в строке: " + t.row + ", позиции: " + t.column + ".");
        } else {
            throw new RuntimeException(message + " в конце файла");
        }
    }

    private Token match(TokenType... expected) {
        if (pos < tokens.size()) {
            Token curr = tokens.get(pos);
            if (Arrays.asList(expected).contains(curr.type)) {
                pos++;
                return curr;
            }
        }
        return null;
    }

    private Token require(TokenType... expected) {
        Token t = match(expected);
        if (t == null)
            error("Ожидалось " + Arrays.toString(expected));
        return t;
    }

    public List<StatementNode> parse() {
        List<StatementNode> program = new ArrayList<>();
        while (pos < tokens.size())
            program.add(parseStatement());
        return program;
    }

    public StatementNode parseStatement() {
        Token op = require(TokenType.IF, TokenType.PRINT);
        switch (op.type) {
            case PRINT:
                Token id = require(TokenType.ID, TokenType.NUMBER);
                require(TokenType.COMMA);
                if(id.type==TokenType.ID) {
                    return new PrintNode(op, new VarNode(id));
                }else if(id.type==TokenType.NUMBER) {
                    return new PrintNode(op, new NumberNode(id));
                }
            case IF:
                ExpressionNode comp = parseCompare();
                require(TokenType.THEN);
                List<StatementNode> body = new ArrayList<>();
                while (match(TokenType.END) == null) {
                    if ( pos < tokens.size()) {
                        StatementNode stmt; /*= parseElse();
if(stmt != null) {
body.add(stmt);
}*/
                        stmt = parseStatement();
                        body.add(stmt);
                    } else error("Ожидался END");
                }
                require(TokenType.COMMA);
                return new IfNode(op, comp, body);
        }
        return null;
    }

    public ExpressionNode parseCompare() {
        ExpressionNode e1 = parseElem();
        Token op;
        while ((op = match(TokenType.MORE, TokenType.LESS, TokenType.EQUAL)) != null) {
            ExpressionNode e2 = parseElem();
            e1 = new BinOpNode(op, e1, e2);
        }
        return e1;
    }

    private ExpressionNode parseElem() {
        Token num = match(TokenType.NUMBER);
        if (num != null)
            return new NumberNode(num);

        Token id = match(TokenType.ID);
        if (id != null)
            return new VarNode(id);

        error("Ожидается число или переменная");
        return null;
    }

    private ElseNode parseElse() {
        Token num = match(TokenType.ELSE);
        if(num != null) {
            List<StatementNode> body = new ArrayList<>();
            body.add(parseStatement());
            ElseNode elseNode = new ElseNode(num, body);
            return elseNode;
        }
        else return null;
    }

}