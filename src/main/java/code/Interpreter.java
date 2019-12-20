package code;

import code.node.ast.*;
import code.node.stmt.ElseNode;
import code.node.stmt.PrintNode;
import code.node.stmt.StatementNode;
import code.node.stmt.IfNode;
import code.tokens.Token;
import code.tokens.TokenType;

import java.util.List;
import java.util.Map;

public class Interpreter {

    private void error(String message){
        throw new RuntimeException(message);
    }

    public void evalProgram(List<StatementNode> program, Map<String, String> vars) {
        for ( StatementNode stmt: program) {
            evalStatement(stmt, vars);
        }
    }

    private void evalStatement( StatementNode stmt, Map<String, String> vars) {
        if (stmt instanceof IfNode) {
            while(evalCompare(((IfNode) stmt).condition, vars)) {
                for ( StatementNode s: ((IfNode) stmt).body) {
                    evalStatement(s, vars);
                }
                break;
            }

// for(StatementNode statementNode : ((IfNode)stmt).body) {
// if(statementNode instanceof IfNode) {
// if(!evalCompare(((IfNode)statementNode).condition,vars)) {
// if (statementNode instanceof ElseNode) {
// evalElse(stmt, vars);
// }
// }
// }
// evalStatement(statementNode, vars);
// }
        }

        if (stmt instanceof PrintNode) {
            System.out.println(evalExpression(((PrintNode) stmt).body, vars));
        }
    }

    private boolean evalCompare( ExpressionNode comp, Map<String, String> vars) {
        String valueL = evalExpression(((BinOpNode)comp).left, vars);
        String valueR = evalExpression(((BinOpNode)comp).right, vars);

        switch (((BinOpNode)comp).op.type) {
            case EQUAL:
                return Integer.parseInt(valueL,16) == Integer.parseInt(valueR,16);
            case MORE:
                return Integer.parseInt(valueL,16) > Integer.parseInt(valueR,16);
            case LESS:
                return Integer.parseInt(valueL,16) < Integer.parseInt(valueR,16);
            default:
                return false;
        }
    }

    private void evalElse(StatementNode statementNode, Map<String, String> vars) {
// if(statementNode instanceof ElseNode) {
        for(StatementNode statementNode1 : ((ElseNode)statementNode).body) {
            evalStatement(statementNode1, vars);
        }
    }

    private String evalExpression(ExpressionNode e, Map<String, String> vars) {
        if(e instanceof NumberNode) return ((NumberNode) e).number.text;
        if(e instanceof VarNode) return vars.get(((VarNode) e).id.text);
//if(e instanceof UnOpNode) return -1 * evalExpression(((UnOpNode) e).expr ,vars);
        error("ошибка в evalExpression");
        return null;
    }
}
