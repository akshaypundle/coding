package pundle.regexp.operator;

import java.util.Stack;
import java.util.function.IntSupplier;

import pundle.regexp.Fragment;
import pundle.regexp.lex.TokenType;
import pundle.regexp.transition.EpsilonTransition;
import pundle.regexp.transition.Transition;

public class OptionalOperator extends UnaryOperator {

    public OptionalOperator(int pos) {
        super(TokenType.OPTIONAL, '?', pos);
    }

    @Override
    public void evaluate(Stack<Fragment> operandStack, IntSupplier stateIdGenerator) {
        Fragment s1 = operandStack.pop();
        Transition tx = new EpsilonTransition(s1.getStart());
        s1.getTransitions().add(tx);
        operandStack.push(s1);
    }

}