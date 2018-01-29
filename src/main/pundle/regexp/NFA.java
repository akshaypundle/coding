package pundle.regexp;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import pundle.regexp.transition.CharConsumingTransition;
import pundle.regexp.transition.EpsilonTransition;
import pundle.regexp.transition.Transition;

public class NFA {
    private final int start;
    private final int end;
    private Map<Integer, Collection<Transition>> transitionsByStart;
    private Map<Integer, Set<Integer>> eClosures;

    public NFA(int start, int end, Collection<Transition> transitions) {
        this.start = start;
        this.end = end;

        ArrayListMultimap<Integer, Transition> transitionsByStartMultimap = ArrayListMultimap.create();
        for (Transition transition : transitions) {
            transitionsByStartMultimap.put(transition.getStartState(), transition);
        }
        this.transitionsByStart = transitionsByStartMultimap.asMap();
        this.eClosures = Maps.newHashMap();

        for (int state : this.transitionsByStart.keySet()) {
            HashSet<Integer> eClosureSet = new HashSet<>();
            eClosure(state, eClosureSet);
            eClosures.put(state, eClosureSet);
        }
        eClosures.put(this.end, Sets.newHashSet(this.end));
    }

    boolean matches(String s) {
        Set<Integer> curStates = this.eClosures.get(start);
        for (int i = 0; i < s.length(); i++) {
            curStates = step(s.charAt(i), curStates);
        }
        return curStates.contains(this.end);
    }

    private Set<Integer> step(char ch, Set<Integer> curStates) {
        Set<Integer> nextStates = new HashSet<>();
        for (int i : curStates) {
            Collection<Transition> transitions = this.transitionsByStart.get(i);
            if (transitions != null) {
                for (Transition tx : transitions) {
                    if (tx instanceof CharConsumingTransition && ((CharConsumingTransition) tx).matches(ch)) {
                        Set<Integer> ec = this.eClosures.get(tx.getEndState());
                        if (ec != null) {
                            nextStates.addAll(ec);
                        }
                    }
                }
            }
        }
        return nextStates;
    }

    private void eClosure(int state, Set<Integer> curStates) {
        if (curStates.contains(state)) {
            return;
        }
        curStates.add(state);
        if (this.transitionsByStart.containsKey(state)) {
            for (Transition tx : this.transitionsByStart.get(state)) {
                if (tx instanceof EpsilonTransition) {
                    eClosure(tx.getEndState(), curStates);
                }
            }
        }
    }

}
