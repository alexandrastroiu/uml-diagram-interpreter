package model.diagrams;

import enums.ActivityNodeType;
import model.elements.ActivityNode;
import model.relationships.ControlFlow;

import java.util.HashSet;
import java.util.Set;

public class ActivityDiagram extends UmlDiagram {

    private Set<ActivityNode> activities;
    private Set<String> swimlanes;
    private Set<String> groups;
    private Set<ControlFlow> controlFlows;
    private int activitiesCount;
    private int swimlanesCount;
    private int loops;
    private int forkCount;
    private int mergeCount;
    private int conditionalNodes;

    // Constructor

    public ActivityDiagram() {
        super();
        activities = new HashSet<>();
        swimlanes = new HashSet<>();
        groups = new HashSet<>();
        controlFlows = new HashSet<>();
        activitiesCount = 0;
        swimlanesCount = 0;
        loops = 0;
        forkCount = 0;
        mergeCount = 0;
        conditionalNodes = 0;
    }

    // Getters, setters

    public Set<ActivityNode> getActivities() {
        return activities;
    }

    public void setActivities(Set<ActivityNode> activities) {
        this.activities = activities;
    }

    public int getActivitiesCount() {
        return activitiesCount;
    }

    public void setActivitiesCount(int activitiesCount) {
        this.activitiesCount = activitiesCount;
    }

    public Set<String> getSwimlanes() {
        return swimlanes;
    }

    public void setSwimlanes(Set<String> swimlanes) {
        this.swimlanes = swimlanes;
    }

    public int getSwimlanesCount() {
        return swimlanesCount;
    }

    public void setSwimlanesCount(int swimlanesCount) {
        this.swimlanesCount = swimlanesCount;
    }

    public Set<ControlFlow> getControlFlows() {
        return controlFlows;
    }

    public void setControlFlows(Set<ControlFlow> controlFlows) {
        this.controlFlows = controlFlows;
    }

    public int getLoops() {
        return loops;
    }

    public void setLoops(int loops) {
        this.loops = loops;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public int getForkCount() {
        return forkCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public int getMergeCount() {
        return mergeCount;
    }

    public void setMergeCount(int mergeCount) {
        this.mergeCount = mergeCount;
    }

    public int getConditionalNodes() {
        return conditionalNodes;
    }

    public void setConditionalNodes(int conditionalNodes) {
        this.conditionalNodes = conditionalNodes;
    }

    public void printActivities() {
        this.activities.stream().filter(activity -> !activity.getType().equals(ActivityNodeType.FORK) && !activity.getType().equals(ActivityNodeType.MERGE) && !activity.getType().equals(ActivityNodeType.CONDITIONAL)).forEach(activity -> System.out.println(activity.getName() + " - " + activity.getType()));
    }
}
