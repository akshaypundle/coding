package pundle.regexp;

import java.util.HashSet;
import java.util.Set;

import pundle.regexp.transition.Transition;

public class Fragment {
    private int start;
    private final Set<Transition> transitions;

    public Fragment(int start) {
        this.start = start;
        this.transitions = new HashSet<>();
    }

    public int getStart() {
        return this.start;
    }

    public Set<Transition> getTransitions() {
        return transitions;
    }

    public void setDanglingEndsTo(int endState) {
        for (Transition transition : this.transitions) {
            if (!transition.hasEnd()) {
                transition.setEndState(endState);
            }
        }
    }
}