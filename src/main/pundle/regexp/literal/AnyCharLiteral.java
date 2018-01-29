package pundle.regexp.literal;

import java.util.function.IntSupplier;

import pundle.regexp.Fragment;
import pundle.regexp.lex.TokenType;
import pundle.regexp.transition.AnyCharTransition;
import pundle.regexp.transition.CharTransition;
import pundle.regexp.transition.Transition;

public class AnyCharLiteral extends Literal {
    public AnyCharLiteral() {
        super(TokenType.CHAR, '.');
    }

    public AnyCharLiteral(int pos) {
        super(TokenType.CHAR, '.', pos);
    }

    @Override
    public Fragment createFragment(IntSupplier stateIdGenerator) {
        int startState = stateIdGenerator.getAsInt();
        Transition charTx = new AnyCharTransition(startState);
        Fragment frag = new Fragment(startState);
        frag.getTransitions().add(charTx);
        return frag;
    }
}
