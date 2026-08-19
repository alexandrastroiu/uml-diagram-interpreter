package model.diagrams;

import enums.StateType;
import model.elements.State;
import model.relationships.Transition;

import java.util.*;

public class StateDiagram extends UmlDiagram {

    private LinkedHashMap<String, State> stateLookup;
    private LinkedHashSet<State> states;
    private LinkedHashSet<Transition> transitions;
    private int transitionCount;
    private int forkCount;
    private int joinCount;
    private int choiceStates;
    private int compositeStates;

    // Constructor

    public StateDiagram () {
        super();
        this.stateLookup = new LinkedHashMap<>();
        this.states = new LinkedHashSet<>();
        this.transitions = new LinkedHashSet<>();
        this.transitionCount = 0;
        this.forkCount = 0;
        this.joinCount = 0;
        this.choiceStates = 0;
        this.compositeStates = 0;
    }

    // Getters, setters

    public LinkedHashMap<String, State> getStateLookup() {
        return stateLookup;
    }

    public void setStateLookup(LinkedHashMap<String, State> stateLookup) {
        this.stateLookup = stateLookup;
    }

    public LinkedHashSet<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(LinkedHashSet<Transition> transitions) {
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

    public LinkedHashSet<State> getStates() {
        return states;
    }

    public void setStates(LinkedHashSet<State> states) {
        this.states = states;
    }

    public int countNodes(StateType type) {
        List<State> nodes = states.stream().filter(state -> state.getType().equals(type)).toList();
        return nodes.size();
    }

    public int countElements() {
        List<State> elements = states.stream().filter(state -> !state.getType().equals(StateType.FORK) &&  !state.getType().equals(StateType.JOIN) && !state.getType().equals(StateType.CHOICE)).toList();
        return elements.size();
    }

    public void addState(State newState) {
        HashMap<String, State> states = getStateLookup();
        String name = newState.getName();
        String alias = newState.getAlias();

        if (states.containsKey(name)) {
            State foundState =  states.get(name);
            foundState.updateState(newState);
            if (!alias.isEmpty()) {
                states.put(alias, newState);
            }
        } else {
            states.put(name, newState);
            if (!alias.isEmpty()) {
                states.put(alias, newState);
            }
        }
    }

    public State findState(State state) {
        HashMap<String, State> states = getStateLookup();
        String name = state.getName();

        if (states.containsKey(name)) {
            return states.get(name);
        }

        return state;
    }

    public void addSetElements(LinkedHashSet<State> set, LinkedHashMap<String, State> map) {
        if (!map.isEmpty()) {
            set.addAll(map.values());
        }
    }

    public void printStates() {
        states.stream().filter(state -> !state.getType().equals(StateType.FORK) &&  !state.getType().equals(StateType.JOIN) && !state.getType().equals(StateType.CHOICE)).forEach(state -> System.out.println(state.getName() + " - " + state.getType()));
    }

    public void printTransitions() {
        transitions.forEach(transition-> System.out.println(transition.getStartState().getName() + " --> " + transition.getEndState().getName()));
    }

    public void printConditions() {
        states.stream().filter(state -> state.getType().equals(StateType.CHOICE)).forEach(condition -> System.out.println(condition.getName()));
    }
}