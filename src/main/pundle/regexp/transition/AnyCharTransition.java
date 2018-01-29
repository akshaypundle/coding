package pundle.regexp.transition;

public class AnyCharTransition extends Transition implements CharConsumingTransition {

    public AnyCharTransition(int startState) {
        super(startState);
    }

    public AnyCharTransition(int startState, int endState, char ch) {
        super(startState, endState);
    }

    @Override
    public boolean matches(char c) {
        return true;
    }
}