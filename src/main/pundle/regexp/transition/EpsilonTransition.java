package pundle.regexp.transition;

public class EpsilonTransition extends Transition {
    public EpsilonTransition(int startState) {
        super(startState);
    }

    public EpsilonTransition(int startState, int endState) {
        super(startState, endState);
    }
}