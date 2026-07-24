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

    // Setters, Getters

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
}
