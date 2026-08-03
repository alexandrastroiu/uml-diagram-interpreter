package model.elements;

import enums.NodeType;

public class UseCaseNode {
    private String name;
    private NodeType type;

    // Getters, setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }
}
