package model.elements;

import enums.StateType;

import java.util.HashSet;
import java.util.Set;

public class State {

    private String name;
    private String description;
    private StateType type;
    private Set<State> substates;

    // Constructor

    public State() {
        this.name = "";
        this.description = "";
        this.type = StateType.SIMPLE;
        this.substates = new HashSet<State>();
    }

    // Getters, setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateType getType() {
        return type;
    }

    public void setType(StateType type) {
        this.type = type;
    }

    public Set<State> getSubstates() {
        return substates;
    }

    public void setSubstates(Set<State> substates) {
        this.substates = substates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void updateState(State state) {
        if (this.name.equals(state.getName())) {
            if (!state.getType().equals(StateType.SIMPLE) && !state.getType().equals(this.type)) {
                setType(state.getType());
            }

            if (!state.getDescription().isEmpty() && !state.getDescription().equals(this.description)) {
                setDescription(state.getDescription());
            }
        }
    }
}
