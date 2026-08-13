package model.relationships;

import model.diagrams.StateDiagram;
import model.elements.State;

public class Transition {
    private State startState;
    private State endState;
    private String transitionDescription;

    // Getters, Setters

    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public State getEndState() {
        return endState;
    }

    public void setEndState(State endState) {
        this.endState = endState;
    }

    public String getTransitionDescription() {
        return transitionDescription;
    }

    public void setTransitionDescription(String transitionDescription) {
        this.transitionDescription = transitionDescription;
    }

    public void addTransitionStates(StateDiagram stateDiagram, State start, State end) {
        State newStart = stateDiagram.findState(start);
        State newEnd = stateDiagram.findState(end);

        setStartState(newStart);
        setEndState(newEnd);
    }
}
