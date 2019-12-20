package code.tokens;

import java.util.regex.Pattern;

public enum TokenType {


    NUMBER("[0-9][a-f0-9]*"),

    IF("if"),
    THEN("then"),
    ELSE("else"),
    PRINT("print"),
    END("end"),
    COMMA(";"),

    ID("[a-zA-Z]"),

    //INC("\\+\\+"),
    //DEC("--"),

    EQUAL("="),
    MORE(">"),
    LESS("<"),

    SPACE("[ \t\r]+"),
    ENDL("\n");

    public final Pattern pattern;

    TokenType(String regexp) {
        pattern = Pattern.compile(regexp);
    }
}
