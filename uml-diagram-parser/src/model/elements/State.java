package model.elements;

import enums.StateType;

import java.util.Set;

public class State {

    private String name;
    private StateType type;
    private Set<State> substates;

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
}
