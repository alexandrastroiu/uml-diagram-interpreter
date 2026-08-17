package model.elements;

import enums.ActivityNodeType;

public class ActivityNode {
    private String name;
    private String alias;
    private ActivityNodeType type;
    private String swimlane;
    private String group;

    // Constructor

    public ActivityNode() {
        this.name = "";
        this.alias = "";
        this.type = ActivityNodeType.ACTIVITY;
        this.swimlane = "";
        this.group = "";
    }

    public ActivityNode(String name, ActivityNodeType type) {
        this.name = name;
        this.type = type;
    }

    public ActivityNode(String name, String alias, ActivityNodeType type, String swimlane) {
        this.name = name;
        this.alias = alias;
        this.type = type;

        if (!swimlane.isEmpty()) {
            this.swimlane = swimlane;
        }
    }

    public ActivityNode(String name, ActivityNodeType type, String swimlane, String group) {
        this.name = name;
        this.type = type;

        if (!swimlane.isEmpty()) {
            this.swimlane = swimlane;
        }

        if(!group.isEmpty()) {
            this.group = group;
        }
    }

    // Getters, setters

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

    public String getSwimlane() {
        return swimlane;
    }

    public void setSwimlane(String swimlane) {
            this.swimlane = swimlane;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}