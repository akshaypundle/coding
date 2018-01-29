package pundle.regexp.operator;

import java.util.Stack;
import java.util.function.IntSupplier;

import pundle.regexp.Fragment;
import pundle.regexp.lex.Token;
import pundle.regexp.lex.TokenType;

public class ConcatOperator extends BinaryOperator {

    public ConcatOperator() {
        super(TokenType.CONCAT, null);
    }

    @Override
    public void evaluate(Stack<Fragment> operandStack, IntSupplier stateIdGenerator) {
        Fragment s2 = operandStack.pop();
        Fragment s1 = operandStack.pop();
        s1.setDanglingEndsTo(s2.getStart());
        s1.getTransitions().addAll(s2.getTransitions());
        operandStack.push(s1);
    }

    @Override
    public void manipulateStack(Stack<Token> operatorStack, Stack<Fragment> operandStack,
            IntSupplier stateIdGenerator) {
        operatorStack.push(this);
    }
}