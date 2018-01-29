package pundle.regexp.operator;

import java.util.Stack;
import java.util.function.IntSupplier;

import pundle.regexp.Fragment;
import pundle.regexp.lex.Token;
import pundle.regexp.lex.TokenType;

public abstract class Operator extends Token {

    public Operator(TokenType type, Character ch) {
        super(type, ch);
    }

    public Operator(TokenType type, Character ch, int pos) {
        super(type, ch, pos);
    }

    public abstract void evaluate(Stack<Fragment> operandStack, IntSupplier stateIdGenerator);
}
