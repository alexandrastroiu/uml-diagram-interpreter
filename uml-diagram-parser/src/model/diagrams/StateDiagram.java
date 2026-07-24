package model.diagrams;

import model.elements.State;
import model.relationships.Transition;

import java.util.Set;

public class StateDiagram extends UmlDiagram {

    private Set<State> states;
    private Set<Transition> transitions;
    private int transitionCount;
    private int forkCount;
    private int joinCount;
    private int choiceStates;
    private int compositeStates;

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public Set<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(Set<Transition> transitions) {
        this.transitions = transitions;
    }

    public int getTransitionCount() {
        return transitionCount;
    }

    public void setTransitionCount(int transitionCount) {
        this.transitionCount = transitionCount;
    }

    public int getForkCount() {
        return forkCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public int getChoiceStates() {
        return choiceStates;
    }

    public void setChoiceStates(int choiceStates) {
        this.choiceStates = choiceStates;
    }

    public int getCompositeStates() {
        return compositeStates;
    }

    public void setCompositeStates(int compositeStates) {
        this.compositeStates = compositeStates;
    }
}
