package pundle.regexp.transition;

public class CharTransition extends Transition implements CharConsumingTransition {
    private final char ch;

    public CharTransition(int startState, char ch) {
        super(startState);
        this.ch = ch;
    }

    public CharTransition(int startState, int endState, char ch) {
        super(startState, endState);
        this.ch = ch;
    }

    @Override
    public boolean matches(char c) {
        return this.ch == c;
    }

    public char getCh() {
        return ch;
    }
}