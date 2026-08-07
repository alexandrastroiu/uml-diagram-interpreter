package model.diagrams;

import enums.ActivityNodeType;
import model.elements.ActivityNode;
import model.relationships.ControlFlow;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ActivityDiagram extends UmlDiagram {

    private LinkedHashSet<ActivityNode> activities;
    private LinkedHashSet<String> swimlanes;
    private LinkedHashSet<String> groups;
    private LinkedHashSet<ControlFlow> controlFlows;
    private int activitiesCount;
    private int swimlanesCount;
    private int loops;
    private int forkCount;
    private int mergeCount;
    private int conditionalNodes;

    // Constructor

    public ActivityDiagram() {
        super();
        activities = new LinkedHashSet<>();
        swimlanes = new LinkedHashSet<>();
        groups = new LinkedHashSet<>();
        controlFlows = new LinkedHashSet<>();
        activitiesCount = 0;
        swimlanesCount = 0;
        loops = 0;
        forkCount = 0;
        mergeCount = 0;
        conditionalNodes = 0;
    }

    // Getters, setters

    public LinkedHashSet<ActivityNode> getActivities() {
        return activities;
    }

    public void setActivities(LinkedHashSet<ActivityNode> activities) {
        this.activities = activities;
    }

    public int getActivitiesCount() {
        return activitiesCount;
    }

    public void setActivitiesCount(int activitiesCount) {
        this.activitiesCount = activitiesCount;
    }

    public LinkedHashSet<String> getSwimlanes() {
        return swimlanes;
    }

    public void setSwimlanes(LinkedHashSet<String> swimlanes) {
        this.swimlanes = swimlanes;
    }

    public int getSwimlanesCount() {
        return swimlanesCount;
    }

    public void setSwimlanesCount(int swimlanesCount) {
        this.swimlanesCount = swimlanesCount;
    }

    public LinkedHashSet<ControlFlow> getControlFlows() {
        return controlFlows;
    }

    public void setControlFlows(LinkedHashSet<ControlFlow> controlFlows) {
        this.controlFlows = controlFlows;
    }

    public int getLoops() {
        return loops;
    }

    public void setLoops(int loops) {
        this.loops = loops;
    }

    public LinkedHashSet<String> getGroups() {
        return groups;
    }

    public void setGroups(LinkedHashSet<String> groups) {
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

    public int countNodes(ActivityNodeType type) {
        List<ActivityNode> nodes = this.activities.stream().filter(activity -> activity.getType().equals(type)).toList();
        return nodes.size();
    }

    public int countElements() {
        List<ActivityNode> elements = this.activities.stream().filter(activity -> !activity.getType().equals(ActivityNodeType.CONDITIONAL) && !activity.getType().equals(ActivityNodeType.FORK) && !activity.getType().equals(ActivityNodeType.MERGE)).toList();
        return elements.size();
    }

    public void printActivities() {
        this.activities.stream().filter(activity -> !activity.getType().equals(ActivityNodeType.FORK) && !activity.getType().equals(ActivityNodeType.MERGE) && !activity.getType().equals(ActivityNodeType.CONDITIONAL)).forEach(activity -> System.out.println(activity.getName() + " - " + activity.getType()));
    }

    public void printSwimlanes() {
        this.swimlanes.forEach(System.out::println);
    }

    public void printGroups() {
        this.groups.forEach(System.out::println);
    }
}
