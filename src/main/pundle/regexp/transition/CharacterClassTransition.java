package pundle.regexp.transition;

import java.util.function.Predicate;

public class CharacterClassTransition extends Transition implements CharConsumingTransition {
    private Predicate<Character> matchingChars;

    public CharacterClassTransition(int startState, Predicate<Character> matchingChars) {
        super(startState);
        this.matchingChars = matchingChars;
    }

    public CharacterClassTransition(int startState,
            int endState,
            Predicate<Character> matchesChars) {
        super(startState, endState);
        this.matchingChars = matchesChars;
    }

    @Override
    public boolean matches(char c) {
        return this.matchingChars.test(c);
    }

    @Override
    public String toString() {
        return this.matchingChars.toString();
    }
}
