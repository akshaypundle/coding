package pundle.regexp.literal;

import java.util.function.IntSupplier;
import java.util.function.Predicate;

import pundle.regexp.Fragment;
import pundle.regexp.lex.TokenType;
import pundle.regexp.transition.CharacterClassTransition;
import pundle.regexp.transition.Transition;

public class CharacterClassLiteral extends Literal {
    private Predicate<Character> matchingChars;

    public CharacterClassLiteral(int pos, Predicate<Character> matchingChars) {
        super(TokenType.CHAR, null, pos);
        this.matchingChars = matchingChars;
    }

    @Override
    public Fragment createFragment(IntSupplier stateIdGenerator) {
        int startState = stateIdGenerator.getAsInt();
        Transition charTx = new CharacterClassTransition(startState, this.matchingChars);
        Fragment frag = new Fragment(startState);
        frag.getTransitions().add(charTx);
        return frag;
    }

}
