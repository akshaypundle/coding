package pundle.regexp.operator;

import java.util.Stack;
import java.util.function.IntSupplier;

import pundle.regexp.Fragment;
import pundle.regexp.lex.TokenType;
import pundle.regexp.transition.EpsilonTransition;
import pundle.regexp.transition.Transition;

public class KleenStarOperator extends UnaryOperator {
    public KleenStarOperator(int pos) {
        super(TokenType.KLEEN_STAR, '*', pos);
    }

    @Override
    public void evaluate(Stack<Fragment> operandStack, IntSupplier stateIdGenerator) {
        int startState = stateIdGenerator.getAsInt();
        Fragment newFrag = new Fragment(startState);

        Transition tx1 = new EpsilonTransition(startState);
        Transition tx2 = new EpsilonTransition(startState);
        newFrag.getTransitions().add(tx1);
        newFrag.getTransitions().add(tx2);

        Fragment frag = operandStack.pop();
        tx1.setEndState(frag.getStart());
        frag.setDanglingEndsTo(tx1.getStartState());
        newFrag.getTransitions().addAll(frag.getTransitions());
        operandStack.push(newFrag);
    }
}