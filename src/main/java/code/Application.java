package code;

import code.node.stmt.StatementNode;
import code.tokens.Token;
import code.tokens.TokenType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
//        String prog = "while x > 0.999999999994458E-4 - 1 do\n" +
//                "  print x;\n" +
//                "  x--;\n" +
//                "  while 1 = 0 do print x; done;\n" +
//                "done;";
        String prog = "if x < 1 then print 6;  else print 4; end; if x > 8 then print 7; end;";
        //String prog = "if x < 1 then print 6; else print 5; end;";
        Map<String, String> vars = new HashMap<>();
        vars.put("x", "9a");
        vars.put("y", "6");

        Lexer l = new Lexer(prog);
        List<Token> tokens = l.lex();
        tokens.removeIf(t -> t.type == TokenType.SPACE);
        tokens.removeIf(t -> t.type == TokenType.ENDL);

        Parser p = new Parser(tokens);
        List<StatementNode> stmts = p.parse();

        Interpreter i = new Interpreter();
        i.evalProgram(stmts, vars);
    }
}
