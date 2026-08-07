package model.elements;

import enums.NodeType;

public class UseCaseNode {
    private String name;
    private String alias;
    private NodeType type;

    // Constructor

    public UseCaseNode(String name, NodeType type) {
        this.name = name;
        this.type = type;
    }

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
