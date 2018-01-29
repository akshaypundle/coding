package pundle.regexp.operator;

import pundle.regexp.lex.TokenType;

public abstract class UnaryOperator extends Operator {
    public UnaryOperator(TokenType type, Character ch) {
        super(type, ch);
    }

    public UnaryOperator(TokenType type, Character ch, int pos) {
        super(type, ch, pos);
    }
}
