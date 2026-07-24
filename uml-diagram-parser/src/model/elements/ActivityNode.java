package model.elements;

import enums.ActivityNodeType;

public class ActivityNode {
    private String name;
    private ActivityNodeType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivityNodeType getType() {
        return type;
    }

    public void setType(ActivityNodeType type) {
        this.type = type;
    }
}
