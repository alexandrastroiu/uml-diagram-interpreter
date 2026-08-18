package model.elements;

import enums.StateType;

public class State {

    private String name;
    private String alias;
    private StateType type;

    // Constructor

    public State() {
        this.name = "";
        this.alias = "";
        this.type = StateType.SIMPLE;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        if (!alias.isEmpty()) {
            this.alias = alias;
        }
    }

    public void updateState(State state) {
        if (this.name.equals(state.getName())) {
            if (!state.getType().equals(StateType.SIMPLE) && !state.getType().equals(this.type)) {
                setType(state.getType());
            }

            if (!state.getAlias().isEmpty() && !state.getAlias().equals(this.alias)) {
                setAlias(state.getAlias());
            }
        }
    }
}
