package pundle.regexp.operator;

import java.util.Stack;
import java.util.function.IntSupplier;

import pundle.regexp.Fragment;
import pundle.regexp.lex.Token;
import pundle.regexp.lex.TokenType;
import pundle.regexp.transition.EpsilonTransition;
import pundle.regexp.transition.Transition;

public class AlternationOperator extends BinaryOperator {

    public AlternationOperator(int pos) {
        super(TokenType.ALTERNATION, '|', pos);
    }

    @Override
    public void manipulateStack(Stack<Token> operatorStack, Stack<Fragment> operandStack,
            IntSupplier stateIdGenerator) {
        while (!operatorStack.isEmpty() && isHigherPrecedence(operatorStack.peek())) {
            Operator popped = (Operator) operatorStack.pop();
            popped.evaluate(operandStack, stateIdGenerator);
        }
        operatorStack.push(this);
    }

    @Override
    public void evaluate(Stack<Fragment> operandStack, IntSupplier stateIdGenerator) {
        int state = stateIdGenerator.getAsInt();
        Fragment s2 = operandStack.pop();
        Fragment s1 = operandStack.pop();

        Fragment newFrag = new Fragment(state);

        Transition tx1 = new EpsilonTransition(state);
        Transition tx2 = new EpsilonTransition(state);

        tx1.setEndState(s1.getStart());
        tx2.setEndState(s2.getStart());
        ;

        newFrag.getTransitions().add(tx1);
        newFrag.getTransitions().add(tx2);
        newFrag.getTransitions().addAll(s1.getTransitions());
        newFrag.getTransitions().addAll(s2.getTransitions());

        operandStack.push(newFrag);
    }

    private boolean isHigherPrecedence(Token t) {
        TokenType tokenType = t.getType();
        return !TokenType.LPAREN.equals(tokenType) && !TokenType.RPAREN.equals(tokenType)
                && !TokenType.ALTERNATION.equals(tokenType);
    }
}