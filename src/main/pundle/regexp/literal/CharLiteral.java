package pundle.regexp.literal;

import java.util.function.IntSupplier;

import pundle.regexp.Fragment;
import pundle.regexp.lex.TokenType;
import pundle.regexp.transition.CharTransition;
import pundle.regexp.transition.Transition;

public class CharLiteral extends Literal {
    public CharLiteral(char ch) {
        super(TokenType.CHAR, ch);
    }

    public CharLiteral(char ch, int pos) {
        super(TokenType.CHAR, ch, pos);
    }

    @Override
    public Fragment createFragment(IntSupplier stateIdGenerator) {
        int startState = stateIdGenerator.getAsInt();
        Transition charTx = new CharTransition(startState, this.getCh());
        Fragment frag = new Fragment(startState);
        frag.getTransitions().add(charTx);
        return frag;
    }
}