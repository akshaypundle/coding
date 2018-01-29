package pundle.regexp.transition;

public abstract class Transition {
    public static final int NO_STATE = -1;
    private int startState;
    private int endState;

    public Transition(int startState) {
        this(startState, NO_STATE);
    }

    public Transition(int startState, int endState) {
        this.startState = startState;
        this.endState = endState;
    }


    public boolean hasEnd() {
        return this.endState != NO_STATE;
    }

    public int getStartState() {
        return startState;
    }

    public void setStartState(int startState) {
        this.startState = startState;
    }

    public int getEndState() {
        return endState;
    }

    public void setEndState(int endState) {
        this.endState = endState;
    }
}