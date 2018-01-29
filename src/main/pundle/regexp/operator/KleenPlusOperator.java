package pundle.regexp.operator;

import java.util.Stack;
import java.util.function.IntSupplier;

import pundle.regexp.Fragment;
import pundle.regexp.lex.TokenType;
import pundle.regexp.transition.EpsilonTransition;
import pundle.regexp.transition.Transition;

public class KleenPlusOperator extends UnaryOperator {
    public KleenPlusOperator(int pos) {
        super(TokenType.KLEEN_PLUS, '+', pos);
    }

    @Override
    public void evaluate(Stack<Fragment> operandStack, IntSupplier stateIdGenerator) {
        Fragment frag = operandStack.pop();
        int nextState = stateIdGenerator.getAsInt();

        Transition tx1 = new EpsilonTransition(nextState);
        Transition tx2 = new EpsilonTransition(nextState);
        tx1.setEndState(frag.getStart());
        frag.setDanglingEndsTo(nextState);
        frag.getTransitions().add(tx1);
        frag.getTransitions().add(tx2);
        operandStack.push(frag);
    }
}